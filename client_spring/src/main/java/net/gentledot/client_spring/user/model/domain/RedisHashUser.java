package net.gentledot.client_spring.user.model.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@RedisHash(value = "redis-hash-user", timeToLive = 30L)
public class RedisHashUser {
    @Id
    private Long id;
    private String name;
    @Indexed
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RedisHashUser from(User user) {
        return RedisHashUser.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
