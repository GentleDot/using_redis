package net.gentledot.client_spring.user.controller;

import net.gentledot.client_spring.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/email")
    public String getUserEmail(@PathVariable("id") Long id) {
        return userService.getUserEmail(id);
    }
}
