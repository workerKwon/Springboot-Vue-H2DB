package com.example.springboot_vue_h2db.repo;

import com.example.springboot_vue_h2db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
