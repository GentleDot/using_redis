package net.gentledot.client_spring.user.repository;

import net.gentledot.client_spring.user.model.domain.RedisHashUser;
import org.springframework.data.repository.CrudRepository;

public interface RedisHashUserRepository extends CrudRepository<RedisHashUser, Long> {
}
