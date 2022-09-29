package com.study.ConstructionCalculatorWeb.controller;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.repo.GroupOfUsersRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class RegistrationController {
    private final GroupOfUsersRepository groupOfUsersRepository;
    private final UserRepository userRepository;

    public RegistrationController(GroupOfUsersRepository groupOfUsersRepository, UserRepository userRepository) {
        this.groupOfUsersRepository = groupOfUsersRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/registry")
    User registry(User user){
        GroupOfUsers group = groupOfUsersRepository.findBygroupName("Пользователь");
        user.setGroupOfUsers(Collections.singleton(group));
        return userRepository.save(user);
    }
    @GetMapping("/")
    List<GroupOfUsers> roles(){
        return groupOfUsersRepository.findAll();
    }
}
