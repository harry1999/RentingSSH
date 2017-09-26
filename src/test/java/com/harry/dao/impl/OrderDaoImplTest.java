package com.harry.dao.impl;

import com.harry.dao.OrderDao;
import com.harry.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class OrderDaoImplTest {

    @Resource
    OrderDao orderDaoImpl;


    @Test
    public void testGetOrderByHouseId () throws Exception {

        List<Order> orderList = orderDaoImpl.getOrderByHouseId(1);

        for ( Order order : orderList ) {

            System.out.println( order );
        }

    }


    @Test
    public void testDeleteOrder () throws Exception {

        orderDaoImpl.deleteOrderById(44);

    }



}