package com.harry.dao.impl;


import com.harry.dao.UserDao;
import com.harry.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {


    @Resource
    SessionFactory sessionFactory;


    public List<User> getUsersByLimit(Map<String, Object> queryParams) throws Exception{

        String hql = " from User user where user.isValid = 1 ";

        if ( queryParams.get("username") != null ) {

            hql += " and user.username like '%" + queryParams.get("username") + "%'";

        }


        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        if ( queryParams.get("start") != null && queryParams.get( "limit")!= null ) {

            query.setFirstResult((Integer) queryParams.get("start")) ;
            query.setMaxResults((Integer) queryParams.get( "limit"));

        }

        List <User> userList = query.list();

        return userList;

    }




    @Override
    public List<User> getUserByName(String username) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from User  user where user.username = ? ";

        Query query = currentSession.createQuery(hql);

        query.setParameter( 0, username );

        return  query.list();

    }




    @Override
    public int deleteUsers(Integer  [] usersId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update  User  user set user.isValid = 0 where user.id in ( :array ) ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList( "array", usersId  );

        int outcome = query.executeUpdate();

        return outcome;

    }




    @Override
    public List<User> getUserById(Integer userId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from User  user where user.isValid = 1 and user.id = " + userId;

        Query query = currentSession.createQuery(hql);

        return query.list();

    }


}
