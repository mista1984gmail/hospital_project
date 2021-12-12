package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class HomeController {
    private final UserServiceImpl userService;

    @Autowired
    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "login";
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();
        String name =authResult.getName();
        User user = userService.findByUsername(name);
        Integer id = user.getId();


        if(role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin"));
        }
        else if(role.contains("ROLE_USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/"+id));
        }
        else if(role.contains("ROLE_DOCTOR")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/doctor"));
        }
        else if(role.contains("ROLE_NURSE")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/nurse"));
        }

    }

    @RequestMapping(value = "/nurse", method = RequestMethod.GET)
    public String nursePage(Model model) {
        return "nurse";
    }
}
