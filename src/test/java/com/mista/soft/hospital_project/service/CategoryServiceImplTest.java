package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.Category;
import com.mista.soft.hospital_project.model.entity.Type;
import com.mista.soft.hospital_project.model.repository.CategoryRepository;
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
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void saveTest(){
        // GIVEN
        Category category = new Category();
        category.setId(1);
        category.setName("Main");
        List<Type> typeList = new ArrayList<>();
        category.setTypes(typeList);

        // WHEN
        categoryService.save(category);

        // THEN
        Assert.assertNotNull(category);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
        Assert.assertTrue(CoreMatchers.is(category.getName())
                .matches("Main"));
    }

    @Test
    public void findAllTest(){
        // GIVEN

        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Main");
        List<Type> typeList1 = new ArrayList<>();
        category1.setTypes(typeList1);

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Operation");
        List<Type> typeList2 = new ArrayList<>();
        category2.setTypes(typeList2);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);

        Mockito.doReturn(categoryList)
                .when(categoryRepository)
                .findAll();

        // WHEN
        categoryService.save(category1);
        categoryService.save(category2);
        List<Category> categories = categoryService.findAll();

        // THEN
        Assert.assertTrue(CoreMatchers.is(categories.size())
                .matches(2));
    }

}
