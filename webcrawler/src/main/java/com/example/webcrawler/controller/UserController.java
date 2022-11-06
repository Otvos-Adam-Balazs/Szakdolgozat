package com.example.webcrawler.controller;

import com.example.webcrawler.dto.UserDto;
import com.example.webcrawler.entities.User;
import com.example.webcrawler.repositories.UserRepository;
import com.example.webcrawler.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto){
           return userService.register(userDto);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @DeleteMapping("/userDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestParam(name = "login") String login){
         userService.deleteByLogin(login);
    }

    @PutMapping("/userUpdate")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
    }
}
