package com.study.ConstructionCalculatorWeb.Service;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUser(String login);
    List<User> getUsers();
    void addRoleToUser(User user, GroupOfUsers group);

}
