package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.RoleDao;
import com.harry.entity.Role;
import com.harry.entity.User;
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

public class RoleServiceImplTest {

    @Resource
    RoleDao roleDaoImpl;

    @Resource
    BaseDao baseDaoImpl;


    @Test
    public void testGetRoles() throws Exception {

        Map<String, Object> queryParams = new HashMap<>();
        User user = baseDaoImpl.getObjectById(User.class, 1);

        queryParams.put("currentUser", user);
        //queryParams.put("start", 1);
        // queryParams.put("limit", 5);

        List<Role> roleList = roleDaoImpl.getRolesByLimit(queryParams);

        System.out.println(roleList);

    }


}