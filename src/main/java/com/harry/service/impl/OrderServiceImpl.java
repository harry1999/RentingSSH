package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.OrderDao;
import com.harry.data.Data;
import com.harry.data.Result;
import com.harry.entity.House;
import com.harry.entity.Order;
import com.harry.entity.OrderDetail;
import com.harry.entity.User;
import com.harry.listener.BusinessListener;
import com.harry.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService, BusinessListener {

    @Resource
    OrderDao orderDaoImpl;


    @Resource
    BaseDao baseDaoImpl;


    @Resource
    BusinessListener businessListener;


    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    @Override
    public Result reserveHouse(Integer houseId, User bookingUser) {

        Result result = new Result();

        List<Order> orderList;

        try {

            orderList = orderDaoImpl.getOrderByHouseId(houseId);

            if (orderList != null && orderList.size() != 0) {

                result.setStatus(Result.SUCCESS);
                result.setMsg(Result.LOCKED);

            } else {

                Order persistentOrder = createOrder(bookingUser, houseId);

                if (persistentOrder != null) {

                    Data data = new Data();
                    data.setE(persistentOrder.getId());

                    result.setStatus(Result.SUCCESS);
                    result.setMsg(Result.OK);
                    result.setData(data);


                } else {

                    result.setStatus(Result.FAILED);
                    result.setMsg(Result.NETWORK_BUSY);

                }


            }

        } catch (Exception e) {

            logger.error("预定房屋出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        return result;

    }


    @Override
    public Result ComfirmReserve(Integer tag, Integer orderId) {

        Result result = new Result();

        if (tag == null || orderId == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.PARAMETER_INVALID);

            return result;

        }


        switch (tag) {

            case 1:

                try {

                    Order order = baseDaoImpl.getObjectById(Order.class, orderId);
                    order.setDealDate(new Date());
                    order.setStatus(Order.DONE);

                    User bookingUser = baseDaoImpl.getObjectById(User.class, order.getBookingUserId());
                    House house = baseDaoImpl.getObjectById(House.class, order.getHouseId());

                    house.setBookingUser(bookingUser);

                    baseDaoImpl.saveObject(order);
                    baseDaoImpl.saveObject(house);

                    businessListener.onDealClose(bookingUser);

                } catch ( Exception e ) {

                    logger.error("确认预租房屋，保存订单出现异常");
                    e.printStackTrace();

                    result.setStatus(Result.FAILED);
                    result.setMsg(Result.NETWORK_BUSY);

                    return result;
                }

                break;

            case 2:

                try {

                    Order order = baseDaoImpl.getObjectById(Order.class, orderId);
                    order.setDealDate(new Date());
                    order.setStatus(Order.CANCELED);

                    baseDaoImpl.saveObject(order);

                } catch ( Exception e ) {

                    logger.error("取消预租房屋出现异常");
                    e.printStackTrace();

                    result.setStatus(Result.FAILED);
                    result.setMsg(Result.NETWORK_BUSY);

                    return result;
                }

                break;

            default:

        }

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return result;

    }


    @Override
    public Result deleteOrderById(Integer orderId) {

        Result result = new Result();

        int outcome;

        try {

            outcome = orderDaoImpl.deleteOrderById(orderId);


        } catch (Exception e) {

            logger.error("删除出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;
        }

        if (outcome != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        return result;
    }


    private Order createOrder(User bookingUser, Integer houseId) throws Exception {

        House house = baseDaoImpl.getObjectById(House.class, houseId);

        Order order = new Order();

        order.setUserId(house.getUser().getId());
        order.setBookingUserId(bookingUser.getId());
        order.setHouseId(houseId);
        order.setCreateDate(new Date());
        order.setIsValid(1);
        order.setStatus(Order.OBLIGATION);


        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setUsername(house.getUser().getUsername());
        orderDetail.setBookingUserName(bookingUser.getUsername());
        orderDetail.setArea(house.getArea());
        orderDetail.setDeposit(house.getDeposit());
        orderDetail.setPrice(house.getPrice());
        orderDetail.setTitle(house.getTitle());

        order.getOrderDetails().add(orderDetail);

        Order persistentOrder = (Order) baseDaoImpl.saveObject(order);

        return persistentOrder;

    }


    @Override
    public void onDealClose(User bookingUser) {

        bookingUser.setPoint(bookingUser.getPoint() + 50);

        Integer point = bookingUser.getPoint();

        String grade =

                point <= 200 ? User.NORMAL : (

                        point <= 500 ? User.BRONZE : (

                                point <= 1000 ? User.SILVER : (

                                        point <= 2000 ? User.GOLDEN : User.DIAMOND

                                )
                        )
                );

        bookingUser.setGrade(grade);

        try {

            baseDaoImpl.saveObject(bookingUser);

        } catch ( Exception e ) {

            logger.error("更新用户积分和会员等级出现异常出现异常");
            e.printStackTrace();

        }

    }


}
