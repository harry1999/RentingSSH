package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.RoleDao;
import com.harry.data.Data;
import com.harry.data.Result;
import com.harry.entity.Privilege;
import com.harry.entity.Role;
import com.harry.entity.RolePrivilege;
import com.harry.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {


    @Resource
    RoleDao roleDaoImpl;


    @Resource
    BaseDao baseDaoImpl;

    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    public Result getRoles(Integer parentId) {

        Result result = new Result();

        List<Role> roleList = null;

        try {

            roleList = roleDaoImpl.getRoles(parentId);

        } catch (Exception e) {

            logger.error("获取角色信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (roleList == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }

        Data data = new Data();
        data.setList(roleList);

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);
        result.setData(data);

        return result;

    }


    @Override
    public Map<String, Object> getRolesByLimit(Map<String, Object> queryParams) {

        try {

            List<Role> roleList = roleDaoImpl.getRolesByLimit(queryParams);

            if (roleList != null && roleList.size() != 0) {

                for (Role role : roleList) {

                    role.setParentRoleName(role.getParentRole().getRoleName());

                }

            }

            queryParams.remove("start");
            queryParams.remove("limit");
            int roleCount = roleDaoImpl.getRolesByLimit(queryParams).size();

            Map<String, Object> rolesInfo = new HashMap<>();
            rolesInfo.put("total", roleCount);
            rolesInfo.put("rows", roleList);

            return rolesInfo;

        } catch (Exception e) {

            logger.error("翻页获取角色信息出现异常");
            e.printStackTrace();

            return null;

        }

    }


    @Override
    public Result createRole(String roleName, String roleDesc, Role parentRole, Integer[] privilegesId) {

        Result result = new Result();

        Role role = new Role();
        role.setRoleName(roleName);
        role.setIsValid(1);
        role.setParentRole(parentRole);

        if (roleDesc != null) {

            role.setRoleDesc(roleDesc);

        }


        for (Integer privilegeId : privilegesId) {

            try {

                Privilege privilege = baseDaoImpl.getObjectById(Privilege.class, privilegeId);
                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setPrivilege(privilege);

                role.getRolePrivileges().add(rolePrivilege);

            } catch (Exception e) {

                logger.error("创建角色信息出现异常");
                e.printStackTrace();

                result.setStatus(Result.FAILED);
                result.setMsg(Result.NETWORK_BUSY);

                return result;

            }

        }


        try {

            baseDaoImpl.saveObject(role);

        } catch (Exception e) {

            logger.error("创建角色信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return result;

    }


    @Override
    public Result updateRole(Role roleToUpdate, Integer[] privilegesId) {

        Result result = new Result();

        try {

            Role role = baseDaoImpl.getObjectById(Role.class, roleToUpdate.getId());
            role.setRoleName(roleToUpdate.getRoleName());
            role.setRoleDesc(roleToUpdate.getRoleDesc());

            Set<RolePrivilege> rolePrivileges = role.getRolePrivileges();
            rolePrivileges.clear();

            for (Integer privilegeId : privilegesId) {

                RolePrivilege rolePrivilege = new RolePrivilege();
                rolePrivilege.setPrivilege(baseDaoImpl.getObjectById(Privilege.class, privilegeId));

                rolePrivileges.add(rolePrivilege);

            }

            baseDaoImpl.saveObject(role);


        } catch (Exception e) {

            logger.error("更新角色信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }


        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return result;

    }


    @Override
    public Result checkRolesCorrelation(Integer[] rolesId) {

        Result result = new Result();

        List<Role> roleList = null;

        try {

            roleDaoImpl.getRelativedRoles(rolesId);

        } catch (Exception e) {

            logger.error("检查角色关联情况出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (roleList.size() == 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.EMPTY);

            return result;

        }

        List<Integer> relativedIds = new ArrayList<>();
        for (Role role : roleList) {

            relativedIds.add(role.getId());

        }

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        Data data = new Data();
        data.setList(relativedIds);

        result.setData(data);

        return result;
    }


    @Override
    public Result delRoles(Integer[] rolesId) {

        Result result = new Result();

        int outcome = 0;

        try {

            roleDaoImpl.deleteRoles(rolesId);

        } catch (Exception e) {

            logger.error("删除角色信息出现异常");
            e.printStackTrace();

            result.setStatus( Result.FAILED );
            result.setMsg( Result.NETWORK_BUSY );

            return result;

        }


        if (outcome == 0) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.ERROR);

        } else {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        }

        return result;

    }


}
