package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.entity.UserStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{
    User addUser(User user);
    User getUser(String login);
    User updateUser(Long id, User user);
    List<User> getUsers();
    List<UserStatus> getUserStatuses();
    List<GroupOfUsers> getRoles();

}
