package com.harry.dao.impl;


import com.harry.dao.OrderDao;
import com.harry.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao  {

    @Resource
    SessionFactory sessionFactory;


    @Override
    public List<Order> getOrderByHouseId(Integer houseId) throws Exception{

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from Order order where  order.isValid = 1 and " +
                "order.status = 1 and order.houseId = ? ";

        Query query = currentSession.createQuery(hql);
        query.setParameter(0, houseId );

        return query.list();

    }



    @Override
    public List<Order> getOrderByUserId(Integer userId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from Order order where  order.isValid = 1 and order.bookingUserId = ? ";

        Query query = currentSession.createQuery(hql);

        query.setParameter(0, userId );

        return query.list();

    }



    @Override
    public int deleteOrderById(Integer orderId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update Order  set  isValid = 0 where id = ? ";

        Query query = currentSession.createQuery(hql);

        query.setParameter(0, orderId );

        int outcome = query.executeUpdate();

        return outcome;

    }



}
