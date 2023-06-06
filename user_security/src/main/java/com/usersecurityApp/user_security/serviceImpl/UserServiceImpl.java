package com.usersecurityApp.user_security.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usersecurityApp.user_security.exception.UserDefinedException;
import com.usersecurityApp.user_security.model.User;
import com.usersecurityApp.user_security.repository.UserRepository;
import com.usersecurityApp.user_security.security.JwtTokenProvider;
import com.usersecurityApp.user_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User saveUser(User user) {

        return  userRepository.save(user);
    }

    @Override
    public User updateUser(User user,Long id) {
      User user1= userRepository.findById(id).orElseThrow(()->new RuntimeException("user id not present"));
      user1.setName(user.getName());
      user1.setUsername(user.getUsername());
      user1.setPassword(user.getPassword());
      user1.setIsAdmin(user.getIsAdmin());
      user1.setIsSuperAdmin(user.getIsSuperAdmin());
        return userRepository.save(user1);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
       Long user1= userRepository.findById(id).orElseThrow(()->new RuntimeException("user id not present")).getId();

        return userRepository.findById(user1);
    }

    @Override
    public String login(User u) throws JsonProcessingException {

        Optional<User> user=  userRepository.findByUsername(u.getUsername());

        if(!user.isPresent()){
            throw  new UserDefinedException("user not found");

        }
  System.out.println(user);

        if(!passwordEncoder.matches(u.getPassword(),user.get().getPassword())){
            throw  new UserDefinedException("incorrect password");
        }
        System.out.println(passwordEncoder);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword().toString()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication, null, user.get(), new ArrayList<>());

        return jwt;
    }


    @Override
    public String register(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userRepository.saveAndFlush(u);
        return "registered successfully";
    }
}
