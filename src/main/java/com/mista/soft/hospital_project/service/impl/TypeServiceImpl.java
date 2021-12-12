package com.mista.soft.hospital_project.service.impl;

import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.repository.TypeRepository;
import com.mista.soft.hospital_project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Type findById(Integer id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public void save(Type type) {
        typeRepository.save(type);
    }
}
