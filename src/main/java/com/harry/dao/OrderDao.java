package com.harry.dao;

import com.harry.entity.Order;

import java.util.List;

public interface OrderDao {

    public List <Order>  getOrderByHouseId  (Integer houseId ) throws Exception;

    public List <Order>  getOrderByUserId  (Integer userId ) throws Exception;

    public int deleteOrderById ( Integer orderId ) throws Exception;

}
