package com.harry.dao.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.DeptDao;
import com.harry.dao.RoleDao;
import com.harry.entity.Department;
import com.harry.entity.Privilege;
import com.harry.entity.Role;
import com.harry.entity.RolePrivilege;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class RoleDaoImplTest {

    @Resource
    RoleDao roleDaoImpl;

    @Resource
    DeptDao deptDaoImpl;

    @Resource
    BaseDao baseDaoImpl;


    @Test
    public void testGetRoles() throws Exception {

        List<Role> roles = roleDaoImpl.getRoles(1);

        System.out.println(roles);


        List<Department> departments = deptDaoImpl.getDepartments(2);

        System.out.println(departments);


    }


    @Test

    public void testSaveRole() throws Exception {

        Role role = new Role();

        role.setRoleName("new");
        role.setRoleDesc("aaa");
        role.setIsValid(1);
        role.setParentRole(baseDaoImpl.getObjectById(Role.class, 1));

        Privilege privilege = baseDaoImpl.getObjectById(Privilege.class, 1);
        RolePrivilege rolePrivilege = new RolePrivilege();
        rolePrivilege.setPrivilege(privilege);

        role.getRolePrivileges().add(rolePrivilege);

        baseDaoImpl.saveObject(role);

    }

}