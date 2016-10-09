package controllers.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Miraculous on 15/7/4.
 */
@Configuration
public class RedisCfg {

	@Value("${redis.defaultExpireTime}")
	private Long defaultExpireTime;

	@Value("${redis.master.host}")
	private String master_host;
	@Value("${redis.master.port}")
	private int master_port;
	@Value("${redis.master.name}")
	private String master_name;

	@Value("${redis.sentinel1.host}")
	private String sentinel_host;
	@Value("${redis.sentinel1.port}")
	private int sentinel_port;

	private RedisConnectionFactory generateDevConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(master_host);
		factory.setPort(master_port);
		factory.setUsePool(true);
		factory.setConvertPipelineAndTxResults(true);
		JedisPoolConfig poolConfig = generatePoolConfig();
		factory.setPoolConfig(poolConfig);

		factory.afterPropertiesSet();

		return factory;
	}

	private RedisConnectionFactory generateReleaseConnectionFactory() {
		RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
		RedisNode master = new RedisNode(master_host, master_port);
		master.setName(master_name);
		Set<RedisNode> sentinels = new HashSet<>(3);
		RedisNode sentinel1 = new RedisNode(sentinel_host, sentinel_port);
		sentinels.add(sentinel1);
		//RedisNode sentinel2 = new RedisNode(sentinel2Host, sentinel2port);
		//sentinels.add(sentinel2);
		sentinelConfiguration.setMaster(master);
		sentinelConfiguration.setSentinels(sentinels);
		JedisPoolConfig poolConfig = generatePoolConfig();
		JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfiguration, poolConfig);
		factory.setHostName(master_host);
		factory.setPort(master_port);
		factory.setTimeout(10000);
		factory.setUsePool(true);
		factory.setConvertPipelineAndTxResults(true);
		factory.afterPropertiesSet();
		return factory;
	}

	private JedisPoolConfig generatePoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(20);
		poolConfig.setMaxTotal(300);
		poolConfig.setMaxWaitMillis(5000);
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}

	@Bean(name = "redisConnectionFactory")
	RedisConnectionFactory factory() {
		if (StringUtils.isEmpty(master_name)) {
			return generateDevConnectionFactory();
		} else {
			//return generateReleaseConnectionFactory();
			return generateDevConnectionFactory();
		}
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		final RedisTemplate<String, Object> template = new RedisTemplate<>();

		template.setEnableTransactionSupport(false);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);

		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		template.setValueSerializer(jdkSerializationRedisSerializer);
		template.setDefaultSerializer(jdkSerializationRedisSerializer);

		template.setConnectionFactory(factory);

		return template;
	}

	@Bean(name = "stringRedisTemplate")
	public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
		final RedisTemplate<String, String> template = new RedisTemplate<>();

		template.setEnableTransactionSupport(true);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setValueSerializer(stringRedisSerializer);
		template.setDefaultSerializer(stringRedisSerializer);

		template.setConnectionFactory(factory);

		return template;
	}

	@Bean(name = "cacheManager")
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(defaultExpireTime);
		//cacheManager.setExpires(ImmutableMap.of("apixDistrictCache", 3600 * 24 * 7L));
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}
}
