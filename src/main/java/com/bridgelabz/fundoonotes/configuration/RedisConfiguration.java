package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

	/**
	 * Redisson includes an implementation of the cache functionality in Spring with
	 * two objects: RedissonSpringCacheManagerand RedissonSpringLocalCachedCacheManager.
	 *  As the name suggests,
	 * RedissonSpringLocalCachedCacheManager includes support for local caching.
	 **/
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

}