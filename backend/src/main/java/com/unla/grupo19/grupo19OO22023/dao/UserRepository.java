package com.unla.grupo19.grupo19OO22023.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.grupo19.grupo19OO22023.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
