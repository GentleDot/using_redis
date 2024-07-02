package net.gentledot.client_spring.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {
    public static final String CACHE1 = "cache1";
    public static final String CACHE2 = "cache2";

    private final ObjectMapper objectMapper;

    public CacheConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        // Object class 정보 삽입 처리
        BasicPolymorphicTypeValidator polymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();
        objectMapper.activateDefaultTyping(polymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

        // cache property 설정
        List<CacheProperty> cacheProperties = List.of(CacheProperty.of(CACHE1, 300), CacheProperty.of(CACHE2, 30));

        return (builder -> {
            for (CacheProperty cacheProperty : cacheProperties) {
                builder.withCacheConfiguration(cacheProperty.getName(),
                        RedisCacheConfiguration.defaultCacheConfig()
                                .disableCachingNullValues()
                                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                                .entryTtl(Duration.ofSeconds(cacheProperty.getTimeToLive()))
                );
            }
        });
    }
}
