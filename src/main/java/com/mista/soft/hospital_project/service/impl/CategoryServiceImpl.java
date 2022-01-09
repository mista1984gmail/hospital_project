package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.repository.CategoryRepository;
import com.mista.soft.hospital_project.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        log.info("Find all categories");
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer id) {
        log.info("Category by id: " + id + " found.");
        return categoryRepository.findById(id).get();
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
        log.info("Category " + category.getName() + " (" + category.getId() + ")" +  " saved.");
    }
}
