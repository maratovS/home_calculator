package com.study.ConstructionCalculatorWeb;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.repo.GroupOfUsersRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ConstructionCalculatorWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstructionCalculatorWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(GroupOfUsersRepository repo, UserRepository userRepository) throws Exception{
		return (String[] args) -> {
			repo.save(new GroupOfUsers("Пользователь"));
			repo.save(new GroupOfUsers("Менеджер"));
			repo.save(new GroupOfUsers("Администратор"));
			User overlord = new User();
			overlord.setLogin("overlord");
			overlord.setName("admin");
			overlord.setEmail("serikssau@gmail.com");
			overlord.setGroupOfUsers(Collections.singleton(repo.findBygroupName("Администратор")));
			userRepository.save(overlord);
		};
	}
}
