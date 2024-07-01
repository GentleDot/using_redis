package net.gentledot.client_spring.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gentledot.client_spring.user.model.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    private final ObjectMapper objectMapper;

    public RedisConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, User> userRedisTemplate = new RedisTemplate<>();
        userRedisTemplate.setConnectionFactory(redisConnectionFactory);
        userRedisTemplate.setKeySerializer(new StringRedisSerializer());
        userRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, User.class));
        return userRedisTemplate;
    }

}
