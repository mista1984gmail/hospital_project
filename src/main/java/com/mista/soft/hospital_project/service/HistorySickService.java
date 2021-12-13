package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;

import java.util.List;

public interface HistorySickService {
    List<HistorySick> findAll();
    HistorySick findById(Integer id);
    void save(HistorySick historySick);
    void deleteById(Integer id);

}
