package net.gentledot.client_spring.user.service;

import net.gentledot.client_spring.user.model.User;
import net.gentledot.client_spring.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createAllUser(List<User> user) {
        List<User> users = userRepository.saveAll(user);
        return users.size() == user.size();
    }
}
