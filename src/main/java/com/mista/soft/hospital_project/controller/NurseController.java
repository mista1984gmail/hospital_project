package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private SendEmailService sendEmailService;

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
        List<User> users = userService.allUsers();
        List<User> patientsList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getAuthorities().toString().contains("ROLE_USER")) {
                patientsList.add(users.get(i));
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

        if(historySick.getType().getCategory().getName().equals("Analyzes")){

        String body = String.format("Good afternoon, %s. Your test result of %s is %s",
                                user.getFirstName(),
                                historySick.getType().getName(),
                                historySick.getAnalysisResults());

        sendEmailService.sendEmail(user.getEmail(),body,"Test results");}

        return "redirect:/nurse/history/{id}";
    }
    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }

    @PostMapping("/nurse/history/getAllOnDate/{id}")
    public String historySickListOfDate(@PathVariable("id") Integer id,@ModelAttribute("dateFromForm") Date dateFromForm, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> allListHistorySick = user.getHistorySicks();
        List<HistorySick> listHistorySick = new ArrayList<>();

        LocalDate date1 = dateFromForm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (int i = 0; i < allListHistorySick.size(); i++) {

            if(allListHistorySick.get(i).getDateOfAction().isAfter(date1)){
                listHistorySick.add(allListHistorySick.get(i));
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        model.addAttribute("dateFromForm", date1);

        return "history_nurse";
    }

    @GetMapping("/nurse/invoice/{id}/{dateFromForm}")
    public String invoice(@PathVariable("id") Integer id,@PathVariable("dateFromForm") Date dateFromForm, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> allListHistorySick = user.getHistorySicks();
        List<HistorySick> listHistorySick = new ArrayList<>();

        LocalDate date1 = dateFromForm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (int i = 0; i < allListHistorySick.size(); i++) {

            if(allListHistorySick.get(i).getDateOfAction().isAfter(date1)){
                listHistorySick.add(allListHistorySick.get(i));
            }
        }
        Date dateNow = new Date();
        LocalDate date2 = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        model.addAttribute("date2", date2);

        return "invoice";
    }

    @GetMapping("/nurse/sendInvoice/{id}")
    public String sendInvoice(@PathVariable("id") Integer id) throws MessagingException {
        User user = userService.findUserById(id);


            String body = String.format("Good afternoon, %s. Your invoice",
                    user.getFirstName());
            String path ="D:\\Downloads\\"+user.getLastName()+"_invoice.pdf";

            sendEmailService.sendMessageWithAttachment(user.getEmail(),"Invoice", body,path);

        return "redirect:/nurse/history/{id}";
    }
}
