package com.example.webcrawler.services;

import com.example.webcrawler.entities.JwtRequest;
import com.example.webcrawler.entities.JwtResponse;
import com.example.webcrawler.entities.User;
import com.example.webcrawler.repositories.UserRepository;
import com.example.webcrawler.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String username = jwtRequest.getLogin();
        String password = jwtRequest.getPassword();
        authenticate(username, password);

      final UserDetails userDetails = loadUserByUsername(username);

      String newGeneratedToken = jwtUtil.generateToken(userDetails);

      User user = userRepository.findUserByLogin(username);

      return new JwtResponse(user, newGeneratedToken);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findUserByLogin(username);

       if(user != null){
           return new org.springframework.security.core.userdetails.User(
                   user.getLogin(),
                   user.getPassword(),
                   getAuthorities(user)
           );
       }else {
            throw new UsernameNotFoundException("Username is not valid");
       }
    }

    private Set getAuthorities(User user){
        Set authorities = new HashSet();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getAuthority()));
        return authorities;
    }

    private void authenticate(String usename, String password) throws  Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usename,password));
        }catch (DisabledException e){
            throw new Exception("User is disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credential From User");
        }
        }

}
