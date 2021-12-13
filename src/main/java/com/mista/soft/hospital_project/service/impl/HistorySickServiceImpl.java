package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.HistorySick;
import com.mista.soft.hospital_project.model.repository.CategoryRepository;
import com.mista.soft.hospital_project.model.repository.HistorySickRepository;
import com.mista.soft.hospital_project.service.HistorySickService;
import org.springframework.stereotype.Service;

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
}
