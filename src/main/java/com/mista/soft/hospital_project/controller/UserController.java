package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/user/history/{id}", method = RequestMethod.GET)
    public String userPage(@PathVariable("id") Integer id, Model model) {
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        return "user";
    }
    @GetMapping("/user/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model){

        User user = userService.findUserById(id);

        model.addAttribute("user", user);

        return "user_form";
    }

    @PostMapping("/user/save/{id}")
    public String saveUser(@PathVariable("id") Integer id, User user){
        userService.update(user);
        return "redirect:/user/history/{id}";
    }



}
