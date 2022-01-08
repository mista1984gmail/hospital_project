package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Type;

import java.util.List;

public interface TypeService {
    List<Type> findAll();
    Type findById(Integer id);
    void save(Type type);
}
