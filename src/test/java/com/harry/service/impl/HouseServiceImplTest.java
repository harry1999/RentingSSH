package com.harry.service.impl;

import com.harry.service.HouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class HouseServiceImplTest {


    @Resource
    HouseService houseServiceImpl;

    @Test
    public void test () {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("start", 0);
        queryParams.put("limit", 100);

        Map<String, Object> houses = houseServiceImpl.getHousesByLimit(queryParams);

        System.out.println( houses );

    }



}