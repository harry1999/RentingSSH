package com.harry.dao.impl;

import com.harry.dao.RoleDao;
import com.harry.entity.Role;
import com.harry.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {


    @Resource
    SessionFactory sessionFactory;


    public List<Role> getRoles(Integer parentId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " from Role role where role.isValid = 1 ";

        hql += parentId != null ? " and role.id = " + parentId + " or  role.parentRole.id = " + parentId : "";

        Query query = currentSession.createQuery(hql);

        return query.list();

    }


    @Override
    public List<Role> getRolesByLimit(Map<String, Object> queryParams) throws Exception {

        String hql = " from Role role where role.isValid = 1 ";

        if (queryParams.get("roleName") != null) {

            hql += " and role.roleName like '%" + queryParams.get("roleName") + "%' ";

        }

        hql += " and role.parentRole.id = " + ((User) queryParams.get("currentUser")).getRole().getId();

        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        if (queryParams.get("start") != null && queryParams.get("limit") != null) {

            query.setFirstResult((Integer) queryParams.get("start"));
            query.setMaxResults((Integer) queryParams.get("limit"));

        }

        List<Role> roleList = query.list();

        return roleList;

    }


    @Override
    public List<Role> getRelativedRoles(Integer[] rolesId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " SELECT role from Role role, User user  where user.role.id in ( :array ) and user.isValid = 1 and role.id = user.role.id ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList("array", rolesId);

        return query.list();

    }


    @Override
    public int deleteRoles(Integer[] rolesId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update Role role set role.isValid = 0 where role.id in ( :array ) ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList("array", rolesId);

        int outcome = query.executeUpdate();

        return outcome;

    }


}
