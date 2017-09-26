package com.harry.service;

import com.harry.data.Result;
import com.harry.entity.User;

public interface OrderService  {


    public Result reserveHouse ( Integer  houseId, User currentUser );

    public Result ComfirmReserve ( Integer tag, Integer orderId );

    public Result deleteOrderById ( Integer orderId );

}
