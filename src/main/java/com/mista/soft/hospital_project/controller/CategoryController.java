package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.service.impl.CategoryServiceImpl;
import com.mista.soft.hospital_project.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private TypeServiceImpl typeService;

    @GetMapping("/nurse/category/new")
    public String showCreateNewCategoryForm(Model model){
        List<Type> listTypes=typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @PostMapping("/nurse/categories/save")
    public String saveCategory(Category category){
        categoryService.save(category);
        return "redirect:/nurse/categories";
    }

    @GetMapping("/nurse/categories")
    public String listCategories(Model model){
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        return "categories";
    }
    @GetMapping("/nurse/categories/edit/{id}")
    public String showEditHistoryForm(@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findById(id);
        List<Type> listTypes=typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("category", category);
        return "category_form";}
}
