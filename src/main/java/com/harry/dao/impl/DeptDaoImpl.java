package com.harry.dao.impl;

import com.harry.dao.DeptDao;
import com.harry.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DeptDaoImpl implements DeptDao {


    @Resource
    SessionFactory sessionFactory;


    public List<Department> getDepartments( Integer parentId ) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from Department dept";

        hql += parentId != null ? " where dept.deptno = "  +   parentId  + "or  dept.parentDepartment.deptno = " + parentId : "";

        Query query = currentSession.createQuery(hql);

        return query.list();
    }


}
