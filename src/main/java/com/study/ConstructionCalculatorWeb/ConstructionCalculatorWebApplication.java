package com.study.ConstructionCalculatorWeb;

import com.study.ConstructionCalculatorWeb.Service.UserService;
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
	public CommandLineRunner run(GroupOfUsersRepository repo, UserRepository userRepository, UserService userService) throws Exception{
		return (String[] args) -> {
			if (repo.findAll().isEmpty()) {
				repo.save(new GroupOfUsers(null, "ROLE_USER"));
				repo.save(new GroupOfUsers(null, "ROLE_MANAGER"));
				repo.save(new GroupOfUsers(null, "ROLE_ADMIN"));
			}
			if (userRepository.findAll().isEmpty()) {
				Set<GroupOfUsers> groups = new HashSet<>(repo.findAll());
				userService.addUser(new User(null, null, null, groups, "Overlordov", "Overlord", "Overlordovich", 93717141, "ololo@gmail.com", "overlord", "123456"));
				userService.addUser(new User(null, null, null, groups, "Толч", "Алексей", "Overlordovich", 93987654, "ololo@gmail.com", "alexey99", "123456"));
				userService.addUser(new User(null, null, null, groups, "Малинин", "Игорь", "Игоревич", 93745678, "ololo@gmail.com", "barbie9925", "123456"));
			}
		};
	}
}