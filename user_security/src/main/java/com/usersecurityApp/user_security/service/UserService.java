package com.usersecurityApp.user_security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usersecurityApp.user_security.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User updateUser(User user,Long id);

    void deleteUser(Long id);

    List<User> getAllUser();

    Optional<User> getById(Long id);


    String login(User u) throws JsonProcessingException;

    String register(User u);




}
