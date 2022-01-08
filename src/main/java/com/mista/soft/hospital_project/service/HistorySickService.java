package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.entity.User;

import java.util.Date;
import java.util.List;

public interface HistorySickService {
    List<HistorySick> findAll();
    HistorySick findById(Integer id);
    void save(HistorySick historySick);
    void deleteById(Integer id);
    List<HistorySick> findAllByDate(User user, Date dateFromForm);
    void executeAppointment(String[] historyId, boolean executeFromDB);
    void historySickAnalysisResults(HistorySick historySick, String[] detailIDs, String[] detailNames, String[] detailValues);


}
