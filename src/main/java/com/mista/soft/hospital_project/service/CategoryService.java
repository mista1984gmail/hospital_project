package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Integer id);
    void save(Category category);

}
