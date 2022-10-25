package com.example.webcrawler.services;

import com.example.webcrawler.dto.UserDto;
import com.example.webcrawler.entities.User;
import com.example.webcrawler.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void deleteByLogin(String login) {
        System.out.println(login);
        userRepository.deleteByLogin(login);
    }

    public void updateUser(UserDto userDto) {
        User user = userRepository.findUserByLogin(userDto.getLogin());

        if (!ObjectUtils.isEmpty(user)) {
            user.setAuthority(userDto.getAuthority());
            userRepository.save(user);
        }

    }

    public String register(UserDto userDto) {
        User user;
        if(ObjectUtils.isEmpty(userDto.getLogin())){
            return "2";
        }else{
           user = userRepository.findUserByLogin(userDto.getLogin());
        }

        if(ObjectUtils.isEmpty(userDto.getPassword())) {
            return "2";
        }
        else {
            if (user == null) {
                User newUser = new User();
                newUser.setLogin(userDto.getLogin());
                newUser.setPassword(bCryptPasswordEncoder
                        .encode(userDto.getPassword()));
                newUser.setAuthority("USER");

                userRepository.save(newUser);
                return "0";
            } else {
                return "1";
            }
        }
    }
}
