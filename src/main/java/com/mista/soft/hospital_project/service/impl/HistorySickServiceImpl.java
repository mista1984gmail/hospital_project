package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.User;
import com.mista.soft.hospital_project.model.repository.HistorySickRepository;
import com.mista.soft.hospital_project.service.HistorySickService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class HistorySickServiceImpl implements HistorySickService {

    private final HistorySickRepository historySickRepository;

    public HistorySickServiceImpl(HistorySickRepository historySickRepository) {
        this.historySickRepository = historySickRepository;
    }

    @Override
    public List<HistorySick> findAll() {
        return historySickRepository.findAll();
    }

    @Override
    public HistorySick findById(Integer id) {
        return historySickRepository.findById(id).get();
    }

    @Override
    public void save(HistorySick historySick) {
        historySickRepository.save(historySick);
    }

    @Override
    public void deleteById(Integer id) {
        historySickRepository.deleteById(id);
    }

    @Override
    public List<HistorySick> findAllByDate(User user, Date dateFromForm) {
        List<HistorySick> allListHistorySick = user.getHistorySicks();
        List<HistorySick> listHistorySick = new ArrayList<>();
        LocalDate date = dateFromForm.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (int i = 0; i < allListHistorySick.size(); i++) {

            if(allListHistorySick.get(i).getDateOfAction().isAfter(date)){
                listHistorySick.add(allListHistorySick.get(i));
            }
        }
        return listHistorySick;
    }

    @Override
    public void executeAppointment(String[] historyId, boolean executeFromDB) {
        if(historyId!=null){
            int idHistory = Integer.parseInt(historyId[0]);
            HistorySick historyFromDB = historySickRepository.findById(idHistory).get();
            executeFromDB = historyFromDB.isExecute();}
    }

    @Override
    public void historySickAnalysisResults(HistorySick historySick, String[] detailIDs, String[] detailNames, String[] detailValues) {

        for(int i = 0; i < detailNames.length; i++){
            if(detailIDs != null && detailIDs.length > 0){
                historySick.setAnalysisResults(Integer.valueOf(detailIDs[i]), detailNames[i], detailValues[i]);
            }else{
                historySick.addAnalysisResults(detailNames[i], detailValues[i]);}
        }
    }
}
