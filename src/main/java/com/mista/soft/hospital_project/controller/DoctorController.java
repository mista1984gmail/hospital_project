package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.service.impl.HistorySickServiceImpl;
import com.mista.soft.hospital_project.service.impl.SendEmailService;
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
import java.util.ArrayList;
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
    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String doctorPage(Model model) {
        List<User> users = userService.allUsers();
        List<User> patientsList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getAuthorities().toString().contains("ROLE_USER")) {
                patientsList.add(users.get(i));
            }
        }
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
    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }

    @PostMapping("/history/getAllOnDate/{id}")
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

    @PostMapping("/history/save/{id}")
    public String saveHistory(@PathVariable("id") Integer id, HistorySick historySick, HttpServletRequest request){
        User user = userService.findUserById(id);
        String[] historyId = request.getParameterValues("historyId");
        boolean executeFromDB=false;
        if(historyId!=null){
        int idHistory = Integer.parseInt(historyId[0]);
        HistorySick historyFromDB = historySickService.findById(idHistory);
        executeFromDB = historyFromDB.isExecute();}

        String[] detailIDs = request.getParameterValues("detailID");
        String[] detailNames = request.getParameterValues("detailName");
        String[] detailValues = request.getParameterValues("detailValue");
        for(int i = 0; i < detailNames.length; i++){
            if(detailIDs != null && detailIDs.length > 0){
                historySick.setAnalysisResults(Integer.valueOf(detailIDs[i]), detailNames[i], detailValues[i]);
            }else{
                historySick.addAnalysisResults(detailNames[i], detailValues[i]);}
        }

        String doctor = request.getUserPrincipal().getName();
        User user2 = userService.findByUsername(doctor);
       String doctorAppointment = "doc. "+user2.getFirstName() + " "+ user2.getLastName();
       historySick.setAppointment(doctorAppointment);
        historySick.setUser(user);
        if(historySick.isExecute()!=executeFromDB){
            historySick.setExecuteAppointment(doctorAppointment);
        }

        user.addHistory(historySick);
        historySickService.save(historySick);

        return "redirect:/doctor/history/{id}";
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



}
