package com.example.testtasknews.repository;

import com.example.testtasknews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
