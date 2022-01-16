package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.RoleServiceImpl;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.mista.soft.hospital_project.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model) {
        List<User> usersList = userService.allUsers();
        model.addAttribute("usersList", usersList);
        return "admin/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserFormAdmin(@PathVariable("id") Integer id, Model model){

        User user = userService.findUserById(id);
        List<Role>roles= roleService.rolesUserToList(user);
        List<Role>roleType= roleService.allRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("roleType", roleType);
        return "admin/admin_user_form";
    }

    @PostMapping("/save/{id}")
    public String saveUserAdmin(@PathVariable("id") Integer id, @Valid User user, HttpServletRequest request, BindingResult bindingResult){

        String[] detailIDs = request.getParameterValues("detailID");
        String[] detailNames = request.getParameterValues("detailName");
        userService.updateAdmin(user, detailIDs, detailNames);

        return "redirect:/admin/admin";
    }

}
