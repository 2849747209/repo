/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package filters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.res.StringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A {@link Filter} that enable client-side cross-origin requests by
 * implementing W3C's CORS (<b>C</b>ross-<b>O</b>rigin <b>R</b>esource
 * <b>S</b>haring) specification for resources. Each {@link HttpServletRequest}
 * request is inspected as per specification, and appropriate response headers
 * are added to {@link HttpServletResponse}.
 * </p>
 *
 * <p>
 * By default, it also sets following request attributes, that help to
 * determine the nature of the request downstream.
 * </p>
 * <ul>
 * <li><b>cors.isCorsRequest:</b> Flag to determine if the request is a CORS
 * request. Set to <code>true</code> if a CORS request; <code>false</code>
 * otherwise.</li>
 * <li><b>cors.request.origin:</b> The Origin URL, i.e. the URL of the page from
 * where the request is originated.</li>
 * <li>
 * <b>cors.request.type:</b> Type of request. Possible values:
 * <ul>
 * <li>SIMPLE: A request which is not preceded by a pre-flight request.</li>
 * <li>ACTUAL: A request which is preceded by a pre-flight request.</li>
 * <li>PRE_FLIGHT: A pre-flight request.</li>
 * <li>NOT_CORS: A normal same-origin request.</li>
 * <li>INVALID_CORS: A cross-origin request which is invalid.</li>
 * </ul>
 * </li>
 * <li><b>cors.request.headers:</b> Request headers sent as
 * 'Access-Control-Request-Headers' header, for pre-flight request.</li>
 * </ul>
 *
 * @see <a href="http://www.w3.org/TR/cors/">CORS specification</a>
 *
 */
public final class CorsFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);
	private static final StringManager sm = StringManager.getManager("org.apache.catalina.filters");


	/**
	 * A {@link Collection} of origins consisting of zero or more origins that
	 * are allowed access to the resource.
	 */
	private final Collection<String> allowedOrigins;

	/**
	 * Determines if any origin is allowed to make request.
	 */
	private boolean anyOriginAllowed;

	/**
	 * A {@link Collection} of methods consisting of zero or more methods that
	 * are supported by the resource.
	 */
	private final Collection<String> allowedMethods;

	/**
	 * A {@link Collection} of headers consisting of zero or more header field
	 * names that are supported by the resource.
	 */
	private final Collection<String> allowedHeaders;

	/**
	 * A {@link Collection} of exposed headers consisting of zero or more header
	 * field names of headers other than the simple response headers that the
	 * resource might use and can be exposed.
	 */
	private final Collection<String> exposedHeaders;

	/**
	 * A supports credentials flag that indicates whether the resource supports
	 * user credentials in the request. It is true when the resource does and
	 * false otherwise.
	 */
	private boolean supportsCredentials;

	/**
	 * Indicates (in seconds) how long the results of a pre-flight request can
	 * be cached in a pre-flight result cache.
	 */
	private long preflightMaxAge;

	public CorsFilter() {
		this.allowedOrigins = new HashSet<>();
		this.allowedMethods = new HashSet<>();
		this.allowedHeaders = new HashSet<>();
		this.exposedHeaders = new HashSet<>();
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException(sm.getString("corsFilter.onlyHttp"));
		}

		// Safe to downcast at this point.
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Method method = Method.valueOf(request.getMethod());

		switch (method) {
			case OPTIONS:
				handlePreflight(request, response, chain);
				break;
			case GET:
			case HEAD:
			case POST:
				handleCrossOrigin(request, response, chain);
				break;
			case PUT:
			case DELETE:
			case TRACE:
			case CONNECT:
				chain.doFilter(request, response);
				//handleInvalid(request, response, chain);
			default:
				break;
		}
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		if (filterConfig != null) {
			String allowedOrigins = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_ORIGINS);
			String allowedHttpMethods = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_METHODS);
			String allowedHttpHeaders = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_HEADERS);
			String exposedHeaders = filterConfig.getInitParameter(PARAM_CORS_EXPOSED_HEADERS);
			String supportsCredentials = filterConfig.getInitParameter(PARAM_CORS_SUPPORT_CREDENTIALS);
			String preflightMaxAge = filterConfig.getInitParameter(PARAM_CORS_PREFLIGHT_MAXAGE);

			parseAndStore(allowedOrigins, allowedHttpMethods, allowedHttpHeaders, exposedHeaders, supportsCredentials, preflightMaxAge);
		} else {
			throw new NullPointerException("filterConfig is null");
		}
	}


	@Override
	public void destroy() {
		// NOOP
	}

	protected void handlePreflight(final HttpServletRequest request,
								   final HttpServletResponse response,
								   final FilterChain chain) throws IOException, ServletException {
		final String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		/**
		 * Section 6.2.1
		 * If the Origin header is not present terminate this set of steps.
		 * The request is outside the scope of this specification.
		 */
		if (origin == null) {
			//common OPTIONS method, let it pass.
			handleNonCORS(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.2
		 * If the value of the Origin header is not a case-sensitive match for any of the values
		 * in list of origins do not set any additional headers and terminate this set of steps.
		 * (1)note:Always matching is acceptable since the list of origins can be unbounded.
		 * (2)note:The Origin header can only contain a single origin as the user agent will not follow redirects.
 		 */
		if (!isOriginAllowed(origin)) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.3
		 * Let method be the value as result of parsing the Access-Control-Request-Method header.
		 * If there is no Access-Control-Request-Method header or if parsing failed,
		 * do not set any additional headers and terminate this set of steps.
		 * The request is outside the scope of this specification.
		 */
		String accessControlRequestMethod = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
		if (accessControlRequestMethod == null) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.4
		 * Let header field-names be the values as result of parsing the Access-Control-Request-Headers headers.
		 * If there are no Access-Control-Request-Headers headers let header field-names be the empty list.
		 * If parsing failed do not set any additional headers and terminate this set of steps.
		 * The request is outside the scope of this specification.
		 */
		Enumeration<String> enumerationAccessControlRequestHeaders = request.getHeaders(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		Set<String> accessControlRequestHeaders = toSet(enumerationAccessControlRequestHeaders);
		if (accessControlRequestHeaders.isEmpty()) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.5
		 * If method is not a case-sensitive match for any of the values in list of methods
		 * do not set any additional headers and terminate this set of steps.
		 * (1)note:Always matching is acceptable since the list of methods can be unbounded.
		 */
		if (!allowedMethods.contains(accessControlRequestMethod)) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.6
		 * If any of the header field-names is not a ASCII case-insensitive match for any of the values
		 * in list of headers do not set any additional headers and terminate this set of steps.
		 * (1)note:Always matching is acceptable since the list of headers can be unbounded.
		 */
		if (!allowedHeaders.containsAll(accessControlRequestHeaders)) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.2.7
		 * If the resource supports credentials add a single Access-Control-Allow-Origin header,
		 * with the value of the Origin header as value,
		 * and add a single Access-Control-Allow-Credentials header with the case-sensitive string "true" as value.
		 * Otherwise, add a single Access-Control-Allow-Origin header,
		 * with either the value of the Origin header or the string "*" as value.
		 * (1)note:The string "*" cannot be used for a resource that supports credentials.
		 */
		/**
		 * mynote:this resource processing model may be wrong
		if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		} else {
			if (anyOriginAllowed) {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			} else {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			}
		}
		 */
		if (anyOriginAllowed) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		} else {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		}
		if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		}

		/**
		 * Section 6.2.8
		 * Optionally add a single Access-Control-Max-Age header with as value the amount of seconds
		 * the user agent is allowed to cache the result of the request.
		 */
		if (preflightMaxAge > 0) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE, String.valueOf(preflightMaxAge));
		}

		/**
		 * Section 6.2.9
		 * If method is a simple method this step may be skipped.
		 * Add one or more Access-Control-Allow-Methods headers consisting of (a subset of) the list of methods.
		 * (1)note:If a method is a simple method it does not need to be listed, but this is not prohibited.
		 * (2)note:Since the list of methods can be unbounded,
		 *    simply returning the method indicated by Access-Control-Request-Method (if supported) can be enough.
		 */
		//response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, accessControlRequestMethod);
		response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, join(allowedMethods, ","));

		/**
		 * Section 6.2.10
		 * If each of the header field-names is a simple header and none is Content-Type, this step may be skipped.
		 * Add one or more Access-Control-Allow-Headers headers consisting of (a subset of) the list of headers.
		 * (1)note:If a header field name is a simple header and is not Content-Type, it is not required to be listed.
		 *    Content-Type is to be listed as only a subset of its values makes it qualify as simple header.
		 * (2)note:Since the list of headers can be unbounded, simply returning supported headers
		 *    from Access-Control-Allow-Headers can be enough.
		 */
		if (!allowedHeaders.isEmpty()) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, join(allowedHeaders, ","));
		}

		// Do not forward the request down the filter chain.
	}

	private void handleCrossOrigin(final HttpServletRequest request,
								   final HttpServletResponse response,
								   final FilterChain chain) throws IOException, ServletException {
		final String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		/**
		 * Section 6.1.1
		 * If the Origin header is not present terminate this set of steps.
		 * The request is outside the scope of this specification.
		 */
		if (origin == null) {
			handleNonCORS(request, response, chain);
			return;
		}

		/**
		 * Section 6.1.2
		 * If the value of the Origin header is not a case-sensitive match for any of the values
		 * in list of origins, do not set any additional headers and terminate this set of steps.
		 * (1)note:Always matching is acceptable since the list of origins can be unbounded.
		 */
		if (!isOriginAllowed(origin)) {
			handleInvalid(request, response, chain);
			return;
		}

		/**
		 * Section 6.1.3
		 * If the resource supports credentials add a single Access-Control-Allow-Origin header,
		 * with the value of the Origin header as value,
		 * and add a single Access-Control-Allow-Credentials header with the case-sensitive string "true" as value.
		 * Otherwise, add a single Access-Control-Allow-Origin header,
		 * with either the value of the Origin header or the string "*" as value.
		 * (1)note:The string "*" cannot be used for a resource that supports credentials.
		 */
		/**
		 * mynote:this resource processing model may be wrong
		 if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		 } else {
			if (anyOriginAllowed) {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			} else {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			}
		 }
		 */
		if (anyOriginAllowed) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		} else {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		}
		if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		}

		/**
		 * If the list of exposed headers is not empty add one or more Access-Control-Expose-Headers headers,
		 * with as values the header field names given in the list of exposed headers.
		 * (1)note:By not adding the appropriate headers resource can also clear the preflight result cache
		 *    of all entries where origin is a case-sensitive match for the value of the Origin header
		 *    and url is a case-sensitive match for the URL of the resource.
		 */
		if ((exposedHeaders != null) && (exposedHeaders.size() > 0)) {
			String exposedHeadersString = join(exposedHeaders, ",");
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeadersString);
		}

		// Forward the request down the filter chain.
		chain.doFilter(request, response);
	}

	private void handleInvalid(final HttpServletRequest request,
							   final HttpServletResponse response,
							   final FilterChain chain) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	@Deprecated
	public void old_doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException(sm.getString("corsFilter.onlyHttp"));
		}

		// Safe to downcast at this point.
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Map<String, String> headers = getRequestHeaders(request);

		// Determines the CORS request type.
		CORSRequestType requestType = checkRequestType(request);

		switch (requestType)
		{
			case SIMPLE:
				// Handles a Simple CORS request.
				this.handleSimpleCORS(request, response, filterChain);
				break;
			case ACTUAL:
				// Handles an Actual CORS request.
				this.handleSimpleCORS(request, response, filterChain);
				break;
			case PRE_FLIGHT:
				// Handles a Pre-flight CORS request.
				this.handlePreflightCORS(request, response, filterChain);
				break;
			case NOT_CORS:
				// Handles a Normal request that is not a cross-origin request.
				this.handleNonCORS(request, response, filterChain);
				break;
			default:
				// Handles a CORS request that violates specification.
				this.handleInvalidCORS(request, response, filterChain);
				break;
		}
	}


	/**
	 * Handles a CORS request of type {@link CORSRequestType}.SIMPLE.
	 *
	 * @param request
	 *            The {@link HttpServletRequest} object.
	 * @param response
	 *            The {@link HttpServletResponse} object.
	 * @param filterChain
	 *            The {@link FilterChain} object.
	 * @throws IOException
	 * @throws ServletException
	 * @see <a href="http://www.w3.org/TR/cors/#resource-requests">Simple
	 *      Cross-Origin Request, Actual Request, and Redirects</a>
	 */
	@Deprecated
	private void handleSimpleCORS(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		// Section 6.1.2
		final String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (!isOriginAllowed(origin)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}

		final String method = request.getMethod();
		if (!allowedMethods.contains(method)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}

		// Section 6.1.3
		// Add a single Access-Control-Allow-Origin header.
		if (anyOriginAllowed && !supportsCredentials) {
			// If resource doesn't support credentials and if any origin is
			// allowed
			// to make CORS request, return header with '*'.
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		} else {
			// If the resource supports credentials add a single
			// Access-Control-Allow-Origin header, with the value of the Origin
			// header as value.
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		}

		// Section 6.1.3
		// If the resource supports credentials, add a single
		// Access-Control-Allow-Credentials header with the case-sensitive
		// string "true" as value.
		if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		}

		// Section 6.1.4
		// If the list of exposed headers is not empty add one or more
		// Access-Control-Expose-Headers headers, with as values the header
		// field names given in the list of exposed headers.
		if ((exposedHeaders != null) && (exposedHeaders.size() > 0)) {
			String exposedHeadersString = join(exposedHeaders, ",");
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeadersString);
		}

		// Forward the request down the filter chain.
		filterChain.doFilter(request, response);
	}


	/**
	 * Handles CORS pre-flight request.
	 *
	 * @param request
	 *            The {@link HttpServletRequest} object.
	 * @param response
	 *            The {@link HttpServletResponse} object.
	 * @param filterChain
	 *            The {@link FilterChain} object.
	 * @throws IOException
	 * @throws ServletException
	 */
	@Deprecated
	private void handlePreflightCORS(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		final String origin = request.getHeader(REQUEST_HEADER_ORIGIN);

		// Section 6.2.2
		if (!isOriginAllowed(origin)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}

		// Section 6.2.3
		String accessControlRequestMethod = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
		if (accessControlRequestMethod == null) {
			handleInvalidCORS(request, response, filterChain);
			return;
		} else {
			accessControlRequestMethod = accessControlRequestMethod.trim();
		}

		// Section 6.2.4
		String accessControlRequestHeadersString = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		Set<String> accessControlRequestHeaders = toSet(accessControlRequestHeadersString, null);

		// Section 6.2.5
		if (!allowedMethods.contains(accessControlRequestMethod)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}

		// Section 6.2.6
		if (!accessControlRequestHeaders.isEmpty()) {
			if (!allowedHeaders.containsAll(accessControlRequestHeaders)) {
				handleInvalidCORS(request, response, filterChain);
				return;
			}
		}

		// Section 6.2.7
		if (supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		} else {
			if (anyOriginAllowed) {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			} else {
				response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			}
		}

		// Section 6.2.8
		if (preflightMaxAge > 0) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE, String.valueOf(preflightMaxAge));
		}

		// Section 6.2.9
		response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, accessControlRequestMethod);

		// Section 6.2.10
		if ((allowedHeaders != null) && (!allowedHeaders.isEmpty())) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, join(allowedHeaders, ","));
		}

		// Do not forward the request down the filter chain.
	}


	/**
	 * Handles a request, that's not a CORS request, but is a valid request i.e.
	 * it is not a cross-origin request. This implementation, just forwards the
	 * request down the filter chain.
	 *
	 * @param request
	 *            The {@link HttpServletRequest} object.
	 * @param response
	 *            The {@link HttpServletResponse} object.
	 * @param filterChain
	 *            The {@link FilterChain} object.
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleNonCORS(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		// Let request pass.
		filterChain.doFilter(request, response);
	}


	/**
	 * Handles a CORS request that violates specification.
	 *
	 * @param request
	 *            The {@link HttpServletRequest} object.
	 * @param response
	 *            The {@link HttpServletResponse} object.
	 * @param filterChain
	 *            The {@link FilterChain} object.
	 */
	@Deprecated
	private void handleInvalidCORS(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.resetBuffer();

		if (log.isDebugEnabled()) {
			String origin = request.getHeader(CorsFilter.REQUEST_HEADER_ORIGIN);
			String method = request.getMethod();
			String accessControlRequestHeaders = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
			// Debug so no need for i18n
			StringBuilder message = new StringBuilder("Invalid CORS request: Origin=");
			message.append(origin);
			message.append(";Method=");
			message.append(method);
			if (accessControlRequestHeaders != null) {
				message.append(";Access-Control-Request-Headers=");
				message.append(accessControlRequestHeaders);
			}
			log.debug(message.toString());
		}
	}


	/**
	 * Joins elements of {@link Set} into a string, where each element is
	 * separated by the provided separator.
	 *
	 * @param elements
	 *            The {@link Set} containing elements to join together.
	 * @param joinSeparator
	 *            The character to be used for separating elements.
	 * @return The joined {@link String}; <code>null</code> if elements
	 *         {@link Set} is null.
	 */
	protected String join(final Collection<String> elements, final String joinSeparator) {
		if (elements == null) {
			return null;
		}

		String separator = joinSeparator != null ? joinSeparator : ",";

		StringBuilder buffer = new StringBuilder();
		for (String element : elements) {
			if (element != null && !element.isEmpty()) {
				buffer.append(separator).append(element);
			}
		}
		if (buffer.length() > 0) {
			buffer.delete(0, separator.length());
		}

		return buffer.toString();
	}


	/**
	 * Takes a comma separated list and returns a Set<String>.
	 *
	 * @param data
	 *            A comma separated list of strings.
	 * @return Set<String>
	 */
	private Set<String> toSet(final String data, final String separator) {
		Set<String> set = new HashSet<String>();
		if (data != null) {
			String trimData = data.trim();
			if (!trimData.isEmpty()) {
				String[] splits = trimData.split(separator != null ? separator : "\\s*,\\s*");
				for (String split : splits) {
					set.add(split);
				}
			}
		}

		return set;
	}

	private Set<String> toSet(Enumeration<String> enumeration) {
		Set<String> set = new HashSet<String>();
		while (enumeration.hasMoreElements()) {
			String val = enumeration.nextElement();
			set.add(val);
		}
		return set;
	}

	/**
	 * Takes a comma separated list and returns a List<String>.
	 *
	 * @param data
	 *            A comma separated list of strings.
	 * @return List<String>
	 */
	private List<String> toList(final String data, final String separator) {
		List<String> list = new ArrayList<String>();
		if (data != null) {
			String trimData = data.trim();
			if (!trimData.isEmpty()) {
				String[] splits = trimData.split(separator != null ? separator : "\\s*,\\s*");
				for (String split : splits) {
					list.add(split);
				}
			}
		}

		return list;
	}


	/**
	 * Determines the request type.
	 * 1.A method is said to be a simple method if it is a case-sensitive match for one of the following:
	 *   (1)GET
	 *   (2)HEAD
	 *   (3)POST
	 * 2.A header is said to be a simple header if the header field name is an ASCII case-insensitive
	 *   match for Accept, Accept-Language, or Content-Language or if it is an ASCII case-insensitive
	 *   match for Content-Type and the header field value media type (excluding parameters) is
	 *   an ASCII case-insensitive match for application/x-www-form-urlencoded, multipart/form-data,
	 *   or text/plain.
	 *   (1)Accept
	 *   (2)Accept-Language
	 *   (3)Content-Language
	 *   (4)Content-Type:application/x-www-form-urlencoded
	 *                   multipart/form-data
	 *                   text/plain
	 * 3.A header is said to be a simple response header if the header field name is an ASCII
	 *   case-insensitive match for one of the following:
	 *   (1)Cache-Control
	 *   (2)Content-Language
	 *   (3)Content-Type
	 *   (4)Expires
	 *   (5)Last-Modified
	 *   (6)Pragma
	 * @param request
	 */
	@Deprecated
	protected CORSRequestType checkRequestType(final HttpServletRequest request) {
		if (request == null) {
			throw new IllegalArgumentException(sm.getString("corsFilter.nullRequest"));
		}

		CORSRequestType requestType = null;
		String originHeader = request.getHeader(REQUEST_HEADER_ORIGIN);
		// Section 6.1.1 and Section 6.2.1
		if (originHeader == null) {
			requestType = CORSRequestType.NOT_CORS;
		} else if (!isValidOrigin(originHeader)) {
			requestType = CORSRequestType.INVALID_CORS;
		} else {
			String method = request.getMethod();
			if (method != null) {
				if ("OPTIONS".equals(method)) {
					String accessControlRequestMethodHeader = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
					if (accessControlRequestMethodHeader != null && !accessControlRequestMethodHeader.isEmpty()) {
						requestType = CORSRequestType.PRE_FLIGHT;
					} else {
						requestType = CORSRequestType.ACTUAL;
					}
				} else if ("GET".equals(method) || "HEAD".equals(method)) {
					requestType = CORSRequestType.SIMPLE;
				} else if ("POST".equals(method)) {
					String mediaType = getMediaType(request.getContentType());
					if (SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES.contains(mediaType)) {
						requestType = CORSRequestType.SIMPLE;
					} else {
						requestType = CORSRequestType.INVALID_CORS;
					}
				} else {
					requestType = CORSRequestType.ACTUAL;
				}
			} else {
				requestType = CORSRequestType.INVALID_CORS;
			}
		}

		return requestType;
	}


	protected boolean isLocalOrigin(HttpServletRequest request, String origin) {
		// Build scheme://host:port from request
		StringBuilder target = new StringBuilder();
		String scheme = request.getScheme();
		if (scheme == null) {
			return false;
		} else {
			scheme = scheme.toLowerCase(Locale.ENGLISH);
		}
		target.append(scheme);
		target.append("://");

		String host = request.getServerName();
		if (host == null) {
			return false;
		}
		target.append(host);

		int port = request.getServerPort();
		if ("http".equals(scheme) && port != 80 || "https".equals(scheme) && port != 443) {
			target.append(':');
			target.append(port);
		}

		return origin.equalsIgnoreCase(target.toString());
	}


	/**
	 * Return the lower case, trimmed value of the media type from the content
	 * type.
	 */
	private String getMediaType(String contentType) {
		if (contentType != null && !contentType.isEmpty()) {
			String result = null;
			int firstSemiColonIndex = contentType.indexOf(';');
			if (firstSemiColonIndex > -1) {
				result = contentType.substring(0, firstSemiColonIndex);
			} else {
				result = contentType;
			}
			result = result.trim().toLowerCase(Locale.ENGLISH);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * Checks if the Origin is allowed to make a CORS request.
	 *
	 * @param origin
	 *            The Origin.
	 * @return <code>true</code> if origin is allowed; <code>false</code>
	 *         otherwise.
	 */
	protected boolean isOriginAllowed(final String origin) {
		if (anyOriginAllowed) {
			return true;
		}

		// If 'Origin' header is a case-sensitive match of any of allowed
		// origins, then return true, else return false.
		return allowedOrigins.contains(origin);
	}


	/**
	 * Parses each param-value and populates configuration variables. If a param
	 * is provided, it overrides the default.
	 *
	 * @param allowedOrigins
	 *            A {@link String} of comma separated origins.
	 * @param allowedHttpMethods
	 *            A {@link String} of comma separated HTTP methods.
	 * @param allowedHttpHeaders
	 *            A {@link String} of comma separated HTTP headers.
	 * @param exposedHeaders
	 *            A {@link String} of comma separated headers that needs to be
	 *            exposed.
	 * @param supportsCredentials
	 *            "true" if support credentials needs to be enabled.
	 * @param preflightMaxAge
	 *            The amount of seconds the user agent is allowed to cache the
	 *            result of the pre-flight request.
	 * @throws ServletException
	 */
	private void parseAndStore(final String allowedOrigins,
							   final String allowedHttpMethods,
							   final String allowedHttpHeaders,
							   final String exposedHeaders,
							   final String supportsCredentials,
							   final String preflightMaxAge) throws ServletException {
		if (allowedOrigins != null) {
			if ("*".equals(allowedOrigins.trim())) {
				this.anyOriginAllowed = true;
			} else {
				this.anyOriginAllowed = false;
				Set<String> setAllowedOrigins = toSet(allowedOrigins, null);
				this.allowedOrigins.addAll(setAllowedOrigins);
			}
		} else {
			this.anyOriginAllowed = true;
		}

		Set<String> setAllowedHttpMethods = null;
		if (allowedHttpMethods != null) {
			setAllowedHttpMethods = toSet(allowedHttpMethods, null);
		} else {
			setAllowedHttpMethods = toSet(DEFAULT_ALLOWED_HTTP_METHODS, null);
		}
		this.allowedMethods.addAll(setAllowedHttpMethods);

		Set<String> setAllowedHttpHeaders = null;
		if (allowedHttpHeaders != null) {
			setAllowedHttpHeaders = toSet(allowedHttpHeaders, null);
		} else {
			setAllowedHttpHeaders = toSet(DEFAULT_ALLOWED_HTTP_HEADERS, null);
		}
		this.allowedHeaders.addAll(setAllowedHttpHeaders);

		Set<String> setExposedHeaders = null;
		if (exposedHeaders != null) {
			setExposedHeaders = toSet(exposedHeaders, null);
		} else {
			setExposedHeaders = toSet(DEFAULT_EXPOSED_HEADERS, null);
		}
		this.exposedHeaders.addAll(setExposedHeaders);

		if (supportsCredentials != null) {
			// For any value other then 'true' this will be false.
			this.supportsCredentials = Boolean.parseBoolean(supportsCredentials);
		} else {
			this.supportsCredentials = DEFAULT_SUPPORTS_CREDENTIALS;
		}

		if (preflightMaxAge != null) {
			try {
				this.preflightMaxAge = Long.parseLong(preflightMaxAge);
			} catch (NumberFormatException e) {
				//this.preflightMaxAge = DEFAULT_PREFLIGHT_MAXAGE;
				throw new ServletException(PARAM_CORS_PREFLIGHT_MAXAGE + " must be a integer");
			}
		} else {
			this.preflightMaxAge = DEFAULT_PREFLIGHT_MAXAGE;
		}
	}

	/**
	 * Checks if a given origin is valid or not. Criteria:
	 * <ul>
	 * <li>If an encoded character is present in origin, it's not valid.</li>
	 * <li>If origin is "null", it's valid.</li>
	 * <li>Origin should be a valid {@link URI}</li>
	 * </ul>
	 *
	 * @param origin
	 * @see <a href="http://tools.ietf.org/html/rfc952">RFC952</a>
	 */
	protected boolean isValidOrigin(String origin) {
		if (origin !=null && !"".equals(origin.trim())) {
			// null is a valid origin
			if ("null".equals(origin)) {
				return true;
			}
			
			// Checks for encoded characters. Helps prevent CRLF injection.
			if (origin.contains("%")) {
				return false;
			}

			URI originURI;

			try {
				originURI = new URI(origin);
			} catch (URISyntaxException e) {
				return false;
			}
			// If scheme for URI is null, return false. Return true otherwise.
			return originURI.getScheme() != null;
		} else {
			return false;
		}
	}

	protected Map<String, String> getRequestHeaders(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		Map<String, String> headers = new ConcurrentHashMap<>();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String val = request.getHeader(key);
			headers.putIfAbsent(key, val);
			log.debug("{}:{}", key, val);
		}

		return headers;
	}


	/**
	 * Determines if any origin is allowed to make CORS request.
	 *
	 * @return <code>true</code> if it's enabled; false otherwise.
	 */
	public boolean isAnyOriginAllowed() {
		return anyOriginAllowed;
	}


	/**
	 * Returns a {@link Set} of headers that should be exposed by browser.
	 */
	public Collection<String> getExposedHeaders() {
		return exposedHeaders;
	}


	/**
	 * Determines is supports credentials is enabled.
	 */
	public boolean isSupportsCredentials() {
		return supportsCredentials;
	}


	/**
	 * Returns the preflight response cache time in seconds.
	 *
	 * @return Time to cache in seconds.
	 */
	public long getPreflightMaxAge() {
		return preflightMaxAge;
	}


	/**
	 * Returns the {@link Set} of allowed origins that are allowed to make
	 * requests.
	 *
	 * @return {@link Set}
	 */
	public Collection<String> getAllowedOrigins() {
		return allowedOrigins;
	}


	/**
	 * Returns a {@link Set} of HTTP methods that are allowed to make requests.
	 *
	 * @return {@link Set}
	 */
	public Collection<String> getAllowedMethods() {
		return allowedMethods;
	}


	/**
	 * Returns a {@link Set} of headers support by resource.
	 *
	 * @return {@link Set}
	 */
	public Collection<String> getAllowedHeaders() {
		return allowedHeaders;
	}


	// -------------------------------------------------- CORS Response Headers
	/**
	 * The Access-Control-Allow-Origin header indicates whether a resource can
	 * be shared based by returning the value of the Origin request header in
	 * the response.
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	/**
	 * The Access-Control-Allow-Credentials header indicates whether the
	 * response to request can be exposed when the omit credentials flag is
	 * unset. When part of the response to a preflight request it indicates that
	 * the actual request can include user credentials.
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

	/**
	 * The Access-Control-Expose-Headers header indicates which headers are safe
	 * to expose to the API of a CORS API specification
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

	/**
	 * The Access-Control-Max-Age header indicates how long the results of a
	 * preflight request can be cached in a preflight result cache.
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

	/**
	 * The Access-Control-Allow-Methods header indicates, as part of the
	 * response to a preflight request, which methods can be used during the
	 * actual request.
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

	/**
	 * The Access-Control-Allow-Headers header indicates, as part of the
	 * response to a preflight request, which header field names can be used
	 * during the actual request.
	 */
	protected static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	// -------------------------------------------------- CORS Request Headers
	/**
	 * The Origin header indicates where the cross-origin request or preflight
	 * request originates from.
	 */
	protected static final String REQUEST_HEADER_ORIGIN = "Origin";

	/**
	 * The Access-Control-Request-Method header indicates which method will be
	 * used in the actual request as part of the preflight request.
	 */
	protected static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

	/**
	 * The Access-Control-Request-Headers header indicates which headers will be
	 * used in the actual request as part of the preflight request.
	 */
	protected static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

	// ----------------------------------------------------- Request attributes
	/**
	 * The prefix to a CORS request attribute.
	 */
	//protected static final String HTTP_REQUEST_ATTRIBUTE_PREFIX = "cors.";

	/**
	 * Attribute that contains the origin of the request.
	 */
	protected static final String HTTP_REQUEST_ATTRIBUTE_ORIGIN = "cors.request.origin";

	/**
	 * Boolean value, suggesting if the request is a CORS request or not.
	 */
	protected static final String HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST = "cors.isCorsRequest";

	/**
	 * Type of CORS request, of type {@link CORSRequestType}.
	 */
	protected static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE = "cors.request.type";

	/**
	 * Request headers sent as 'Access-Control-Request-Headers' header, for
	 * pre-flight request.
	 */
	protected static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS = "cors.request.headers";

	// -------------------------------------------------------------- Constants
	/**
	 * Enumerates varies types of CORS requests. Also, provides utility methods
	 * to determine the request type.
	 */
	protected static enum CORSRequestType {
		/**
		 * A simple HTTP request, i.e. it shouldn't be pre-flighted.
		 */
		SIMPLE,
		/**
		 * A HTTP request that needs to be pre-flighted.
		 */
		ACTUAL,
		/**
		 * A pre-flight CORS request, to get meta information, before a
		 * non-simple HTTP request is sent.
		 */
		PRE_FLIGHT,
		/**
		 * Not a CORS request, but a normal request.
		 */
		NOT_CORS,
		/**
		 * An invalid CORS request, i.e. it qualifies to be a CORS request, but
		 * fails to be a valid one.
		 */
		INVALID_CORS
	}

	/**
	 * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">RFC2616</a>
 	 */
	protected static enum Method {
		OPTIONS,
		GET,
		HEAD,
		POST,
		PUT,
		DELETE,
		TRACE,
		CONNECT
	}

	/**
	 * {@link Collection} of media type values for the Content-Type header that
	 * will be treated as 'simple'. Note media-type values are compared ignoring
	 * parameters and in a case-insensitive manner.
	 *
	 * @see  <a href="http://www.w3.org/TR/cors/#terminology"
	 *       >http://www.w3.org/TR/cors/#terminology</a>
	 */
	protected static final Collection<String> SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES = new HashSet<>(Arrays.asList("application/x-www-form-urlencoded", "multipart/form-data", "text/plain"));

	// ------------------------------------------------ Configuration Defaults
	/**
	 * By default, all origins are allowed to make requests.
	 */
	protected static final String DEFAULT_ALLOWED_ORIGINS = "*";

	/**
	 * By default, following methods are supported: GET, POST, HEAD and OPTIONS.
	 */
	protected static final String DEFAULT_ALLOWED_HTTP_METHODS = "GET,POST,HEAD,OPTIONS";

	/**
	 * By default, time duration to cache pre-flight response is 30 mins.
	 */
	protected static final long DEFAULT_PREFLIGHT_MAXAGE = 1800;

	/**
	 * By default, support credentials is turned on.
	 */
	protected static final boolean DEFAULT_SUPPORTS_CREDENTIALS = true;

	/**
	 * By default, following headers are supported:
	 * Origin,Accept,X-Requested-With, Content-Type,
	 * Access-Control-Request-Method, and Access-Control-Request-Headers.
	 */
	protected static final String DEFAULT_ALLOWED_HTTP_HEADERS = "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers";

	/**
	 * By default, none of the headers are exposed in response.
	 */
	protected static final String DEFAULT_EXPOSED_HEADERS = "";

	// ----------------------------------------Filter Config Init param-name(s)
	/**
	 * Key to retrieve allowed origins from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_ALLOWED_ORIGINS = "cors.allowed.origins";

	/**
	 * Key to retrieve support credentials from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_SUPPORT_CREDENTIALS = "cors.support.credentials";

	/**
	 * Key to retrieve exposed headers from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_EXPOSED_HEADERS = "cors.exposed.headers";

	/**
	 * Key to retrieve allowed headers from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_ALLOWED_HEADERS = "cors.allowed.headers";

	/**
	 * Key to retrieve allowed methods from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_ALLOWED_METHODS = "cors.allowed.methods";

	/**
	 * Key to retrieve preflight max age from {@link FilterConfig}.
	 */
	protected static final String PARAM_CORS_PREFLIGHT_MAXAGE = "cors.preflight.maxage";
}
