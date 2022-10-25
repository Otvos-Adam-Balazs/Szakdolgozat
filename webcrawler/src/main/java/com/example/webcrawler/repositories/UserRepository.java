package com.example.webcrawler.repositories;

import com.example.webcrawler.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> deleteByLogin(String login);
    User findUserByLogin(String login);
}
