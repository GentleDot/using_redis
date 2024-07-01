package net.gentledot.client_spring.user.service;

import io.micrometer.common.util.StringUtils;
import net.gentledot.client_spring.user.model.domain.User;
import net.gentledot.client_spring.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, User> userRedisTemplate;

    public UserService(UserRepository userRepository, StringRedisTemplate stringRedisTemplate, RedisTemplate<String, User> userRedisTemplate) {
        this.userRepository = userRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userRedisTemplate = userRedisTemplate;
    }

    public boolean createAllUser(List<User> user) {
        List<User> users = userRepository.saveAll(user);
        return users.size() == user.size();
    }

    public String getUserEmail(Long id) {
        String result = "";
        String userEmailRedisKey = "users:%d:email".formatted(id);

        String targetUserEmail = stringRedisTemplate.opsForValue().get(userEmailRedisKey);

        if (StringUtils.isNotEmpty(targetUserEmail)) {
            return targetUserEmail;
        }

        String targetUserEmailData = userRepository.findEmailById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        stringRedisTemplate.opsForValue().set(userEmailRedisKey, targetUserEmailData, Duration.ofSeconds(30));
        result = targetUserEmailData;
        return result;
    }

    public User getUser(long id) {
        String userKey = "users:%d".formatted(id);
        User user = userRedisTemplate.opsForValue()
                .get(userKey);

        if (user != null) {
            return user;
        }

        User userData = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRedisTemplate.opsForValue().set(userKey, userData, Duration.ofSeconds(30));
        return userData;
    }
}
