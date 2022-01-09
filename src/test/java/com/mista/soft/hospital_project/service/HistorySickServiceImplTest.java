package com.mista.soft.hospital_project.service;

import com.mista.soft.hospital_project.model.entity.*;
import com.mista.soft.hospital_project.model.repository.HistorySickRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistorySickServiceImplTest {
    @Autowired
    private HistorySickService historySickService;

    @MockBean
    private HistorySickRepository historySickRepository;

    @Test
    public void deleteByIdTest(){
        // GIVEN
        HistorySick historySick = new HistorySick();
        historySick.setId(1);

        // WHEN
        historySickService.save(historySick);
        historySickService.deleteById(1);

        // THEN
        Mockito.verify(historySickRepository, Mockito.times(1)).deleteById(1);

    }
    @Test
    public void saveTest(){
        // GIVEN
        HistorySick historySick = new HistorySick();
        historySick.setId(1);

        // WHEN
        historySickService.save(historySick);

        // THEN
        Assert.assertNotNull(historySick);
        Mockito.verify(historySickRepository, Mockito.times(1)).save(historySick);
        Assert.assertTrue(CoreMatchers.is(historySick.getId())
                .matches(1));
    }

    @Test
    public void findAllTest(){
        // GIVEN
        HistorySick historySick1 = new HistorySick();
        historySick1.setId(1);

        HistorySick historySick2 = new HistorySick();
        historySick2.setId(2);

        List<HistorySick> historySickList = new ArrayList<>();
        historySickList.add(historySick1);
        historySickList.add(historySick2);

        Mockito.doReturn(historySickList)
                .when(historySickRepository)
                .findAll();

        // WHEN
        List<HistorySick> historySicks = historySickService.findAll();

        // THEN
        Assert.assertTrue(CoreMatchers.is(historySicks.size())
                .matches(2));
    }
}

