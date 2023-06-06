package com.usersecurityApp.user_security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usersecurityApp.user_security.model.User;
import com.usersecurityApp.user_security.payload.ResponseWrapperDTO;
import com.usersecurityApp.user_security.repository.UserRepository;
import com.usersecurityApp.user_security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userController")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(HttpServletRequest httpServletRequest) {

        return "hi";
    }


    @PostMapping("/createUser")
    public ResponseEntity<ResponseWrapperDTO> createUser(@RequestBody User user,HttpServletRequest httpServletRequest){
        User user1=   userService.saveUser(user);
        ResponseWrapperDTO responseWrapperDTO=new ResponseWrapperDTO(HttpServletResponse.SC_CREATED, "post Data", user1, httpServletRequest.getServletPath());

        return new   ResponseEntity<ResponseWrapperDTO>(responseWrapperDTO, HttpStatus.CREATED);

    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ResponseWrapperDTO>updateUser(@RequestBody User user, @PathVariable Long id,HttpServletRequest httpServletRequest){
        User user1=userService.updateUser(user,id);
        ResponseWrapperDTO responseWrapperDTO=new ResponseWrapperDTO(HttpServletResponse.SC_CREATED, "post Data", user1, httpServletRequest.getServletPath());

        return new   ResponseEntity<ResponseWrapperDTO>(responseWrapperDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/deleteUser/{id}")
        public ResponseEntity<ResponseWrapperDTO> deleteUser(@PathVariable Long id,HttpServletRequest  httpServletRequest){
       userService.deleteUser(id);

        ResponseWrapperDTO responseWrapperDTO=new ResponseWrapperDTO(HttpServletResponse.SC_OK, "DELETE DATA BY ID ", "USER DELETE SUCCESSFULLY "+id, httpServletRequest.getServletPath());

        return new   ResponseEntity<ResponseWrapperDTO>(responseWrapperDTO, HttpStatus.OK);
        }

        @GetMapping("/getById/{id}")
        public ResponseEntity<ResponseWrapperDTO> getById(@PathVariable Long id,HttpServletRequest httpServletRequest){
       // return userService.getById(id);
            ResponseWrapperDTO responseWrapperDTO=new ResponseWrapperDTO(HttpServletResponse.SC_OK, "GET DATA BY ID", userService.getById(id), httpServletRequest.getServletPath());

            return new   ResponseEntity<ResponseWrapperDTO>(responseWrapperDTO, HttpStatus.OK);
        }

        @GetMapping("/getAllUser")
        @Secured("")
        public ResponseEntity<ResponseWrapperDTO> getAllUser(HttpServletRequest httpServletRequest){
      //  return userService.getAllUser();
            ResponseWrapperDTO responseWrapperDTO=new ResponseWrapperDTO(HttpServletResponse.SC_OK, "GET ALL USER", userService.getAllUser(), httpServletRequest.getServletPath());

            return new   ResponseEntity<ResponseWrapperDTO>(responseWrapperDTO, HttpStatus.OK);

        }

    @PostMapping("/allow/login")
    public String login(@RequestBody User u, HttpServletRequest httpServletRequest) throws JsonProcessingException {

        return userService.login(u);
    }


    @PostMapping("/allow/register")
    public String register(@RequestBody User u, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        try {
            return userService.register(u);
        }catch (Exception e){
            return e.getMessage();
        }



    }

    @GetMapping("getUserSuperAdmin")
    @Secured("ROLE_SUPERADMIN")
    public List<User> getUserSuperAdmin(HttpServletRequest httpServletRequest) {

        return userRepository.findAllCust();
    }
}
