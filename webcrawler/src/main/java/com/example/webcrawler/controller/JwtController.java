package com.example.webcrawler.controller;

import com.example.webcrawler.entities.JwtRequest;
import com.example.webcrawler.entities.JwtResponse;
import com.example.webcrawler.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    JwtService jwtService;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
      return  jwtService.createJwtToken(jwtRequest);
    }
}
