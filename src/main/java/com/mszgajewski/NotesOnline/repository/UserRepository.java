package com.mszgajewski.NotesOnline.repository;

import com.mszgajewski.NotesOnline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);
}
