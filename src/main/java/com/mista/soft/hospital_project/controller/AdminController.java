package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mista.soft.hospital_project.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @GetMapping("/admin/edit/{id}")
    public String showEditUserFormAdmin(@PathVariable("id") Integer id, Model model){

        User user = userService.findUserById(id);
        Set<Role> listRoles = new HashSet<>();

        listRoles=user.getRoles();

        List<Role>roles= new ArrayList<>();
        Iterator<Role> iterator = listRoles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            roles.add(role);
        }

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin_user_form";
    }

    @PostMapping("/admin/save/{id}")
    public String saveUserAdmin(@PathVariable("id") Integer id, User user, HttpServletRequest request){

        String[] detailIDs = request.getParameterValues("detailID");
        String[] detailNames = request.getParameterValues("detailName");

        for(int i = 0; i < detailNames.length; i++){
            if(detailIDs != null && detailIDs.length > 0){
                String role = detailNames[i];
                switch (role){
                    case "ROLE_USER":
                        user.setRoles(Collections.singleton(new Role(1, detailNames[i])));
                        break;
                    case "ROLE_ADMIN":
                        user.setRoles(Collections.singleton(new Role(2, detailNames[i])));
                        break;
                    case "ROLE_DOCTOR":
                        user.setRoles(Collections.singleton(new Role(3, detailNames[i])));
                        break;
                    case "ROLE_NURSE":
                        user.setRoles(Collections.singleton(new Role(4, detailNames[i])));
                        break;
                }
            }

        }
        userService.updateAdmin(user);
        return "redirect:/admin/users";
    }

}
