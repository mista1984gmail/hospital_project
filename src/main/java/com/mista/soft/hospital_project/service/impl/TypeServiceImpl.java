package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.repository.TypeRepository;
import com.mista.soft.hospital_project.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> findAll() {
        log.info("Find all types");
        return typeRepository.findAll();
    }

    @Override
    public Type findById(Integer id) {
        log.info("Type by id: " + id + " found.");
        return typeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void save(Type type) {
        typeRepository.save(type);
        log.info("Type " + type.getName() + " (" + type.getId() + ")" +  " saved.");
    }
}
