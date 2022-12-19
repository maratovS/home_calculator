package com.study.ConstructionCalculatorWeb.service.ServiceImpl;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.entity.UserStatus;
import com.study.ConstructionCalculatorWeb.repo.GroupOfUsersRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import com.study.ConstructionCalculatorWeb.repo.UserStatusRepository;
import com.study.ConstructionCalculatorWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupOfUsersRepository groupOfUsersRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User updateUser(Long id, User user) {
        User savedUser = userRepository.getById(id);
        if(!savedUser.getSurname().equals(user.getSurname()))
            savedUser.setSurname(user.getSurname());
        if(!savedUser.getName().equals(user.getName()))
            savedUser.setName(user.getName());
        if(!savedUser.getPatronymic().equals(user.getPatronymic()))
            savedUser.setPatronymic(user.getPatronymic());
        if(savedUser.getTelephoneNumber() != user.getTelephoneNumber())
            savedUser.setTelephoneNumber(user.getTelephoneNumber());
        if(!savedUser.getEmail().equals(user.getEmail()))
            savedUser.setEmail(user.getEmail());
        if(!savedUser.getLogin().equals(user.getLogin()))
            savedUser.setLogin(user.getLogin());
        if(!savedUser.getPassword().equals(user.getPassword()))
            savedUser.setPassword(user.getPassword());
        if (!savedUser.getGroupOfUsers().equals(user.getGroupOfUsers()))
            savedUser.setGroupOfUsers(user.getGroupOfUsers());
        if (!savedUser.getStatus().equals(user.getStatus()))
            savedUser.setStatus(user.getStatus());
        return userRepository.save(savedUser);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserStatus> getUserStatuses() {
        return userStatusRepository.findAll();
    }

    @Override
    public List<GroupOfUsers> getRoles() {
        return groupOfUsersRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException("User not found in database!");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getGroupOfUsers().forEach(group -> authorities.add(new SimpleGrantedAuthority(group.getGroupName())));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
