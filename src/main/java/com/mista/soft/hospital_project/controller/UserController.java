package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
    public String userPage(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        return "/user/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/user/user_form";
    }

    @PostMapping("/save/{id}")
    public String saveUser(@PathVariable("id") Integer id, User user){
        userService.update(user);
        return "redirect:/user/history/{id}";
    }



}
