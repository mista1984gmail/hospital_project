package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.CategoryServiceImpl;
import com.mista.soft.hospital_project.service.impl.HistorySickServiceImpl;
import com.mista.soft.hospital_project.service.impl.TypeServiceImpl;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NurseController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    HistorySickServiceImpl historySickService;

    @RequestMapping(value = "/nurse", method = RequestMethod.GET)
    public String nursePage(Model model) {
        return "nurse";
    }

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
        List<Type> listTypes = category.getTypes();
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("category", category);
        return "category_edit";}

    @GetMapping("/nurse/patients")
    public String listOfPatients( Model model){
        List<User> patientsList = userService.allUsers();
        for (int i = 0; i < patientsList.size(); i++) {
            if(!patientsList.get(i).getAuthorities().toString().contains("ROLE_USER")){
                patientsList.remove(i);
            }
        }
        model.addAttribute("patientsList", patientsList);

        return "patients_nurse";
    }
    @GetMapping("/nurse/history/{id}")
    public String historySickList(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);

        return "history_nurse";
    }
    @GetMapping("/nurse/history/{userId}/edit/{historyId}")
    public String showEditHistoryForm(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){

        HistorySick history = historySickService.findById(historyId);
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        model.addAttribute("history", history);

        List<Type> listTypes=typeService.findAll();

        model.addAttribute("listTypes", listTypes);
        return "history_form_nurse";
    }
    @PostMapping("/nurse/history/save/{id}")
    public String saveHistory(@PathVariable("id") Integer id, HistorySick historySick, HttpServletRequest request){
        User user = userService.findUserById(id);
        String[] detailIDs = request.getParameterValues("detailID");
        String[] detailNames = request.getParameterValues("detailName");
        String[] detailValues = request.getParameterValues("detailValue");
        for(int i = 0; i < detailNames.length; i++){
            if(detailIDs != null && detailIDs.length > 0){
                historySick.setAnalysisResults(Integer.valueOf(detailIDs[i]), detailNames[i], detailValues[i]);
            }else{
                historySick.addAnalysisResults(detailNames[i], detailValues[i]);}
        }
        historySick.setUser(user);
        user.addHistory(historySick);
        historySickService.save(historySick);

        return "redirect:/nurse/history/{id}";
    }
}
