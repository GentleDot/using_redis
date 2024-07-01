package net.gentledot.client_spring.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    public JedisPool createJedisPool() {
//        return new JedisPool("127.0.0.1", 6379);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setJmxEnabled(false); // Disable JMX registration
        return new JedisPool(poolConfig, "127.0.0.1", 6379);
    }

}
