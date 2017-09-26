package com.harry.service;

import com.harry.data.Result;
import com.harry.entity.Role;

import java.util.Map;

public interface RoleService {

    public Result getRoles(Integer parentId);

    public Map<String, Object> getRolesByLimit(Map<String, Object> queryParams);

    public Result createRole(String roleName, String roleDesc, Role parentRole, Integer[] privilegesId);

    public Result updateRole(Role roleToUpdate, Integer[] privilegesId);

    public Result checkRolesCorrelation(Integer[] rolesId);

    public Result delRoles(Integer[] rolesId);

}
