package net.gentledot.client_spring.user.service;

import io.micrometer.common.util.StringUtils;
import net.gentledot.client_spring.user.model.User;
import net.gentledot.client_spring.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    public UserService(UserRepository userRepository, JedisPool jedisPool) {
        this.userRepository = userRepository;
        this.jedisPool = jedisPool;
    }

    public boolean createAllUser(List<User> user) {
        List<User> users = userRepository.saveAll(user);
        return users.size() == user.size();
    }

    public String getUserEmail(Long id) {
        String result = "";
//        return userRepository.findEmailById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));

        /*
        start
        * 1. request to cache
        * 2. miss -> request to db
        * 3. cache
        end
        * */
        try (Jedis redis = jedisPool.getResource()) {
            String userEmailRedisKey = "users:%d:email".formatted(id);
            String targetUserEmail = redis.get(userEmailRedisKey);

            if (StringUtils.isNotBlank(targetUserEmail)) {
                return targetUserEmail;
            }
            String targetUserEmailData = userRepository.findEmailById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

//            redis.set(userEmailRedisKey, targetUserEmailData);
            redis.setex(userEmailRedisKey, 30, targetUserEmailData); // TTL 30 seconds
            result = targetUserEmailData;

        } catch (Exception e) {
            throw new RuntimeException("user not found.", e);
        }

        return result;
    }
}
