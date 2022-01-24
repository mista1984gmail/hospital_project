package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.CategoryService;
import com.mista.soft.hospital_project.service.HistorySickService;
import com.mista.soft.hospital_project.service.TypeService;
import com.mista.soft.hospital_project.service.UserService;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/nurse")
public class NurseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;
    @Autowired
    private HistorySickService historySickService;
    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(value = "/nurse", method = RequestMethod.GET)
    public String nursePage(Model model) {
        List<User> patientsList = userService.allUsersWithRoleUser();
        model.addAttribute("patientsList", patientsList);
        return "nurse/nurse";
    }

    @GetMapping("/category/new")
    public String showCreateNewCategoryForm(Model model){
        List<Type> listTypes=typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("category", new Category());
        return "nurse/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category){
        categoryService.save(category);
        return "redirect:/nurse/categories";
    }

    @GetMapping("/categories")
    public String listCategories(Model model){
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        return "nurse/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String showEditHistoryForm(@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findById(id);
        List<Type> listTypes = category.getTypes();
        model.addAttribute("listTypes", listTypes);
        model.addAttribute("category", category);
        return "nurse/category_edit";
    }

    @GetMapping("/history/{id}")
    public String historySickList(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);

        return "nurse/history_nurse";
    }

    @GetMapping("/history/{userId}/edit/{historyId}")
    public String showEditHistoryForm(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){
        HistorySick history = historySickService.findById(historyId);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("history", history);
        List<Type> listTypes=typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        return "nurse/history_form_nurse";
    }

    @PostMapping("/history/getAllOnDate/{id}")
    public String historySickListOfDate(@PathVariable("id") Integer id,@ModelAttribute("dateFromForm") Date dateFromForm, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = historySickService.findAllByDate(user,dateFromForm);
        LocalDate dateToForm = dateFromForm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        model.addAttribute("dateFromForm", dateToForm);
        return "nurse/history_nurse";
    }

    @GetMapping("/invoice/{id}/{dateFromForm}")
    public String invoice(@PathVariable("id") Integer id,@PathVariable("dateFromForm") Date dateFromForm, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = historySickService.findAllByDate(user,dateFromForm);
        Date dateNow = new Date();
        LocalDate dateToForm = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        model.addAttribute("dateToForm", dateToForm);
        return "nurse/invoice";
    }

    @GetMapping("/sendInvoice/{id}")
    public String sendInvoice(@PathVariable("id") Integer id) throws MessagingException {
        User user = userService.findUserById(id);
        sendEmailService.sendMessageWithAttachment(
                user.getEmail(),
                "Invoice",
                user.getFirstName(),
                user.getLastName());
        return "redirect:/nurse/history/{id}";
    }

    @GetMapping("/types")
    public String listTypes(Model model){
        List<Type> listTypes = typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        return "nurse/types";
    }

    @GetMapping("/types/new")
    public String showTypeNewForm(Model model){
        List<Category> listCategories = categoryService.findAll();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("type", new Type());

        return "nurse/type_form";
    }

    @PostMapping("/types/save")
    public String saveType(Type type){
        typeService.save(type);
        return "redirect:/nurse/types";
    }

    @PostMapping("/history/save/{id}")
    public String saveHistory(@PathVariable("id") Integer id, HistorySick historySick, HttpServletRequest request){
        User user = userService.findUserById(id);

        //getting from database execute of appointment
        String[] historyId = request.getParameterValues("historyId");
        boolean executeFromDB=false;
        historySickService.executeAppointment(historyId, executeFromDB);

        //getting test results from the form
        String[] detailIDs = request.getParameterValues("detailID");
        String[] detailNames = request.getParameterValues("detailName");
        String[] detailValues = request.getParameterValues("detailValue");
        historySickService.historySickAnalysisResults(historySick, detailIDs, detailNames, detailValues);

        if (!historySick.getType().getName().contains("Operation")){

            //setting who completed the appointment
            User nurse = userService.findByUsername(request.getUserPrincipal().getName());
            String nurseAppointment = "nurse "+nurse.getFirstName() + " "+ nurse.getLastName();
            if (historySick.isExecute()!=executeFromDB){
                historySick.setExecuteAppointment(nurseAppointment);
            }

            //save history sick
            historySick.setUser(user);
            user.addHistory(historySick);
            historySickService.save(historySick);

            //send email with analyzes
            if (historySick.getType().getCategory().getName().equals("Analyzes")){
                sendEmailService.sendEmail(user.getEmail(),
                        user.getFirstName(),
                        historySick.getType().getName(),
                        historySick.getAnalysisResults(),
                        "Test results");
            }
        }

        return "redirect:/nurse/history/{id}";
    }

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }
}
