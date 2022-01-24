package com.mista.soft.hospital_project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mista.soft.hospital_project.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
