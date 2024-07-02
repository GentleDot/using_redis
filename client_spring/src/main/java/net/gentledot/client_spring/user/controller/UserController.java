package net.gentledot.client_spring.user.controller;

import net.gentledot.client_spring.user.model.domain.RedisHashUser;
import net.gentledot.client_spring.user.model.domain.User;
import net.gentledot.client_spring.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("{id}")
    public RedisHashUser getRedisHashUser(@PathVariable("id") Long id) {
        return userService.getRedisHashUser(id);
    }

    @GetMapping("{id}/email")
    public String getUserEmail(@PathVariable("id") Long id) {
        return userService.getUserEmail(id);
    }
}
