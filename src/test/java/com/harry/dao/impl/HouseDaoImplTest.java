package com.harry.dao.impl;

import com.harry.data.Messager;
import com.harry.entity.House;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class HouseDaoImplTest {


    @Resource
    HouseDaoImpl houseDaoImpl;

    @Test
    public void testGetHouses () throws Exception {

        Messager messager = new Messager();

        messager.setTitle("中关村");
        messager.setPriceRange(2);
        messager.setAreaRange(1);
        messager.setStreet(1);
        messager.setType(1);
        messager.setIndex(1);

        System.out.println(houseDaoImpl.getHouses(messager));

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("start", 0);
        queryParams.put("limit", 100);

        List<House> houseList = houseDaoImpl.getHousesByLimit(queryParams);

        System.out.println( houseList );

    }

}