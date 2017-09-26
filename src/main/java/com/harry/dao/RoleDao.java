package com.harry.dao;

import com.harry.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleDao  {

    public List<Role> getRoles (Integer  parentId ) throws Exception;

    public List <Role> getRolesByLimit (Map<String, Object> queryParams ) throws  Exception;

    public List <Role> getRelativedRoles(Integer [] rolesId )  throws Exception;

    public int deleteRoles ( Integer [] rolesId ) throws  Exception;

}
