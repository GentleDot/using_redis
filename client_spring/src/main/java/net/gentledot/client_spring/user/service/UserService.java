package net.gentledot.client_spring.user.service;

import io.micrometer.common.util.StringUtils;
import net.gentledot.client_spring.user.model.User;
import net.gentledot.client_spring.user.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public UserService(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public boolean createAllUser(List<User> user) {
        List<User> users = userRepository.saveAll(user);
        return users.size() == user.size();
    }

    public String getUserEmail(Long id) {
        String result = "";
        String userEmailRedisKey = "users:%d:email".formatted(id);

        String targetUserEmail = redisTemplate.opsForValue().get(userEmailRedisKey);

        if (StringUtils.isNotEmpty(targetUserEmail)) {
            return targetUserEmail;
        }

        String targetUserEmailData = userRepository.findEmailById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        redisTemplate.opsForValue().set(userEmailRedisKey, targetUserEmailData, Duration.ofSeconds(30));
        result = targetUserEmailData;
        return result;
    }
}
