package com.mista.soft.hospital_project.model.repository;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorySickRepository extends JpaRepository<HistorySick, Integer> {
}
