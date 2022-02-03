package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.TypeService;
import com.mista.soft.hospital_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();
        String name =authResult.getName();
        User user = userService.findByUsername(name);
        Integer id = user.getId();


        if(user.isActive()) {

            if (role.contains("ROLE_ADMIN")) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/admin"));
            } else if (role.contains("ROLE_USER")) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/history/" + id));
            } else if (role.contains("ROLE_DOCTOR")) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/doctor/doctor"));
            } else if (role.contains("ROLE_NURSE")) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/nurse/nurse"));
            }
        }
        else {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/404"));
        }

    }

    @GetMapping("/price")
    public String price(Model model){
        List<Type> listTypes = typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        return "price";
    }

    @RequestMapping(value = "/limited_access", method = RequestMethod.GET)
    public String accessDenied() {
        return "limited_access";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String accessActivation() {
        return "404";
    }

}
