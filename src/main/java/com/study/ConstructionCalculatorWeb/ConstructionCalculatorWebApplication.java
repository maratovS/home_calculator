package com.study.ConstructionCalculatorWeb;

import com.study.ConstructionCalculatorWeb.service.UserService;
import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.repo.GroupOfUsersRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ConstructionCalculatorWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionCalculatorWebApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner run(GroupOfUsersRepository repo, UserRepository userRepository, UserService userService){
		return (String[] args) -> {
			if (repo.findAll().isEmpty()) {
				repo.save(new GroupOfUsers(null, "ROLE_USER"));
				repo.save(new GroupOfUsers(null, "ROLE_MANAGER"));
				repo.save(new GroupOfUsers(null, "ROLE_ADMIN"));
			}
			if (userRepository.findAll().isEmpty()) {
				Set<GroupOfUsers> groups = new HashSet<>(repo.findAll());
				userService.addUser(new User(null, null, groups, "Overlordov", "Overlord", "Overlordovich", 9093717141L, "ololo@gmail.com", "overlord", "123456"));
				userService.addUser(new User(null, null, groups, "Толч", "Алексей", "Overlordovich", 9093987654L, "ololo@gmail.com", "alexey99", "123456"));
				userService.addUser(new User(null, null, groups, "Малинин", "Игорь", "Игоревич", 9093745678L, "ololo@gmail.com", "barbie9925", "123456"));
			}
		};
	}
}
