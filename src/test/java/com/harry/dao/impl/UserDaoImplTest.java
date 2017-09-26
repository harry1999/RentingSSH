package com.harry.dao.impl;

import com.harry.dao.UserDao;
import com.harry.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserDaoImplTest {

    @Resource
    UserDao userDaoImpl;

    @Test
    public void testDeleteUsers() throws Exception {

        Integer[] array = {45, 46};
        int i = userDaoImpl.deleteUsers(array);

    }


    @Test
    public void testGetUseById() throws Exception {

        List<User> userList = userDaoImpl.getUserById(35);

        System.out.println(userList.get(0));

    }


}