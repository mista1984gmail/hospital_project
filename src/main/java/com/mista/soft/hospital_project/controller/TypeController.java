package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.service.impl.CategoryServiceImpl;
import com.mista.soft.hospital_project.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TypeController {
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/nurse/types")
    public String listTypes(Model model){
        List<Type> listTypes = typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        return "types";
    }
    @GetMapping("/nurse/types/new")
    public String showTypeNewForm(Model model){
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("type", new Type());

        return "type_form";
    }

    @PostMapping("/nurse/types/save")
    public String saveType(Type type){
        typeService.save(type);
        return "redirect:/nurse/types";
    }


}
