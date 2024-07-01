package net.gentledot.client_spring;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.client_spring.user.model.domain.User;
import net.gentledot.client_spring.user.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class ClientSpringApplication implements ApplicationRunner {

	private final UserService userService;

    public ClientSpringApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
		SpringApplication.run(ClientSpringApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		User tester1 = User.builder().name("tester1").email("tester1@example.com").build();
		User tester2 = User.builder().name("tester2").email("tester2@example.com").build();
		User tester3 = User.builder().name("tester3").email("tester3@example.com").build();
		User tester4 = User.builder().name("tester4").email("tester4@example.com").build();
		User tester5 = User.builder().name("tester5").email("tester5@example.com").build();
		boolean allUserInserted = userService.createAllUser(List.of(tester1, tester2, tester3, tester4, tester5));
		log.info("allUserInserted: {}", allUserInserted);
	}
}
