package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {
        return "admin";
    }
    @GetMapping("/admin/users")
    public String listOfPatients( Model model){
        List<User> usersList = userService.allUsers();
        model.addAttribute("usersList", usersList);

        return "users";
    }

}
