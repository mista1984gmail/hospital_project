package com.mista.soft.hospital_project.model.repository;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorySickRepository extends JpaRepository<HistorySick, Integer> {
}
