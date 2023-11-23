package com.duc.register_login.repository;

import com.duc.register_login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public Long countById(Integer id);
}
