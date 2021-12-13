package com.mista.soft.hospital_project.model.repository;

import com.mista.soft.hospital_project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
