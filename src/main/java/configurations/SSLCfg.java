package configurations;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by admin on 2016/11/4.
 */
@Configuration
public class SSLCfg {

	//@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");

				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");

				securityConstraint.addCollection(collection);

				context.addConstraint(securityConstraint);
			}
		};

		tomcat.addAdditionalTomcatConnectors(httpConnector());
		tomcat.addAdditionalTomcatConnectors(httpsConnector());
		return tomcat;
	}

	//@Bean
	public Connector httpConnector() {
		Connector connector = new Connector();
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

	//@Bean
	public Connector httpsConnector() {
		Connector connector = new Connector();
		connector.setScheme("https");
		connector.setPort(8443);
		connector.setSecure(true);
		//connector.setRedirectPort(8443);
		return connector;
	}
}
