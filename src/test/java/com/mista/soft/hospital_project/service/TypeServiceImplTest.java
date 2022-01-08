package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.repository.TypeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeServiceImplTest {
    @Autowired
    private TypeService typeService;

    @MockBean
    private TypeRepository typeRepository;

    @Test
    public void saveTest(){
        // GIVEN
        Type type = new Type();
        type.setId(1);
        type.setName("Vitamin c");
        type.setPrice(0.99);
        List<Type> typeList = new ArrayList<>();
        Category category = new Category(1,"Medicines", typeList);
        type.setCategory(category);

        // WHEN
        typeService.save(type);

        // THEN
        Assert.assertNotNull(type);
        Mockito.verify(typeRepository, Mockito.times(1)).save(type);
        Assert.assertTrue(CoreMatchers.is(type.getName())
                .matches("Vitamin c"));
    }

    @Test
    public void findAllTest(){
        // GIVEN

        Type type1 = new Type();
        type1.setId(1);
        type1.setName("Vitamin c");
        type1.setPrice(0.99);
        List<Type> typeList = new ArrayList<>();
        Category category = new Category(1,"Medicines", typeList);
        type1.setCategory(category);

        Type type2 = new Type();
        type2.setId(2);
        type2.setName("Vitamin b");
        type2.setPrice(0.50);
        type2.setCategory(category);

        List<Type> typeListToTest = new ArrayList<>();
        typeListToTest.add(type1);
        typeListToTest.add(type2);

        Mockito.doReturn(typeListToTest)
                .when(typeRepository)
                .findAll();

        // WHEN
        typeService.save(type1);
        typeService.save(type2);
        List<Type> types = typeService.findAll();

        // THEN
        Assert.assertTrue(CoreMatchers.is(types.size())
                .matches(2));
    }




}
