package com.mista.soft.hospital_project.controller;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.entity.User;
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
public class DoctorController {
    @Autowired
    HistorySickServiceImpl historySickService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    TypeServiceImpl typeService;

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public String doctorPage(Model model) {
        return "doctor";
    }

    @GetMapping("/doctor/patients")
    public String listOfPatients( Model model){
        List<User> patientsList = userService.allUsers();
        for (int i = 0; i < patientsList.size(); i++) {
            if(!patientsList.get(i).getAuthorities().toString().contains("ROLE_USER")){
                patientsList.remove(i);
            }
        }
        model.addAttribute("patientsList", patientsList);

        return "patients";
    }

    @GetMapping("/doctor/history/{id}")
    public String historySickList(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<HistorySick> listHistorySick = user.getHistorySicks();
        model.addAttribute("user", user);
        model.addAttribute("listHistorySick", listHistorySick);

        return "history";
    }

    @GetMapping("/doctor/history/new/{id}")
    public String showNewHistoryForm(@PathVariable("id") Integer id, Model model){
        User user = userService.findUserById(id);
        List<Type> listTypes = typeService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("history", new HistorySick());
        model.addAttribute("listTypes", listTypes);
        return "history_form";
    }

    @PostMapping("/doctor/history/save/{id}")
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

        return "redirect:/doctor/history/{id}";
    }

    @GetMapping("doctor/{userId}/history/delete/{historyId}")
    public String deleteHistory(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){
        historySickService.deleteById(historyId);
        return "redirect:/doctor/history/{userId}";
    }

    @GetMapping("/doctor/history/{userId}/edit/{historyId}")
    public String showEditHistoryForm(@PathVariable("historyId") Integer historyId, @PathVariable("userId") Integer userId, Model model){

        HistorySick history = historySickService.findById(historyId);
        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        model.addAttribute("history", history);

        List<Type> listTypes=typeService.findAll();

        model.addAttribute("listTypes", listTypes);
        return "history_form";
    }



}
