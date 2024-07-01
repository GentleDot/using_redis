package net.gentledot.client_spring.user.repository;

import net.gentledot.client_spring.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
