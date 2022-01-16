package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.HistorySickServiceImpl;
import com.mista.soft.hospital_project.service.impl.TypeServiceImpl;
import com.mista.soft.hospital_project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    HistorySickServiceImpl historySickService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    TypeServiceImpl typeService;

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String doctorPage(Model model) {
        List<User> patientsList = userService.allUsersWithRoleUser();
        model.addAttribute("patientsList", patientsList);
        return "doctor/doctor";
    }

    @GetMapping("/history/{id}")
    public String historySickList(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
       model.addAttribute("dateFromForm", new Date());
        return "doctor/history";
    }

    @PostMapping("/history/getAllOnDate/{id}")
    public String historySickListOfDate(@PathVariable("id") Integer id,@ModelAttribute("dateFromForm") Date dateFromForm, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = historySickService.findAllByDate(user,dateFromForm);
        LocalDate dateToForm = dateFromForm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);
        model.addAttribute("dateFromForm", dateToForm);
        return "doctor/history";
    }

    @GetMapping("/history/new/{id}")
    public String showNewHistoryForm(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<Type> listTypes = typeService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("history", new HistorySick());
        model.addAttribute("listTypes", listTypes);
        return "doctor/history_form";
    }

    @GetMapping("/{userId}/history/delete/{historyId}")
    public String deleteHistory(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){
        historySickService.deleteById(historyId);
        return "redirect:/doctor/history/{userId}";
    }

    @GetMapping("/history/{userId}/edit/{historyId}")
    public String showEditHistoryForm(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){
        HistorySick history = historySickService.findById(historyId);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("history", history);
        List<Type> listTypes=typeService.findAll();
        model.addAttribute("listTypes", listTypes);
        return "doctor/history_form";
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

        //setting who completed the appointment
        User doctor = userService.findByUsername(request.getUserPrincipal().getName());
        String doctorAppointment = "doc. "+doctor.getFirstName() + " "+ doctor.getLastName();
        historySick.setAppointment(doctorAppointment);
        if(historySick.isExecute()!=executeFromDB){
            historySick.setExecuteAppointment(doctorAppointment);
        }

        //save history sick
        historySick.setUser(user);
        user.addHistory(historySick);
        historySickService.save(historySick);

        return "redirect:/doctor/history/{id}";
    }

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }



}
