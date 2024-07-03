package net.gentledot.pubsub.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    private final RedisTemplate<String, String> redisTemplate;

    public PublishController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/events/users/unregister")
    void publishUserUnregisteredEvent() {
        redisTemplate.convertAndSend("users:unregister", "500");
    }
}
