package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.UserDao;
import com.harry.data.Result;
import com.harry.entity.Department;
import com.harry.entity.Role;
import com.harry.entity.User;
import com.harry.service.UserService;
import com.harry.utils.EncryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    BaseDao baseDaoImpl;

    @Resource
    UserDao userDaoImpl;


    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    public List<User> getUsersByLimit(Map<String, Object> queryParams) {

        List<User> userList = null;

        try {

            userList = userDaoImpl.getUsersByLimit(queryParams);

        } catch (Exception e) {

            logger.error("翻页获取用户信息出现异常");
            e.printStackTrace();

            return null;

        }

        return userList;

    }


    public Result createUser(User user) {

        Result result = new Result();

        Object obj;

        try {

            Map<String, Object> queryParams = new HashMap();

            queryParams.put("roleName", user.getRoleName());
            Role role = baseDaoImpl.getObjectsByFields(Role.class, queryParams).get(0);

            user.setRole(role);

            if (role.getId() == 3) {

                user.setPoint(0);
                user.setGrade(User.NORMAL);

            }

            queryParams.remove("roleName");
            queryParams.put("name", user.getDepartmentName());
            user.setDepartment(baseDaoImpl.getObjectsByFields(Department.class, queryParams).get(0));

            user.setPassword(EncryptUtil.encode(user.getPassword()));
            user.setCreateDate(new Date());
            user.setIsValid(1);

            obj = baseDaoImpl.saveObject(user);

        } catch (Exception e) {

            logger.error("创建用户信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (obj == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.ERROR);

        } else {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        }

        return result;

    }


    @Override
    public Result register(User user) {

        Result result = new Result();

        try {

            user.setPassword(EncryptUtil.encode(user.getPassword()));
            user.setRole(baseDaoImpl.getObjectById(Role.class, 3));
            user.setDepartment(baseDaoImpl.getObjectById(Department.class, 6));
            user.setPoint(0);
            user.setGrade(User.NORMAL);
            user.setCreateDate(new Date());
            user.setIsValid(1);

            baseDaoImpl.saveObject(user);

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);


        } catch (Exception e) {

            logger.error("用户注册出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        return result;
    }


    @Override
    public Result getUserByName(String username) {

        List<User> userList = null;
        Result result = new Result();

        try {

            userList = userDaoImpl.getUserByName(username);

        } catch (Exception e) {

            logger.error("获取用户信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);
        }


        if (userList == null || userList.size() == 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.USER_NOT_EXITS);

        } else {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.USER_EXITS);

        }

        return result;

    }


    @Override
    public Result updateUserByUser(User user) {

        Result result = new Result();
        Object obj = null;

        try {

            User targetUser = baseDaoImpl.getObjectById(User.class, user.getId());
            targetUser.setUsername(user.getUsername());
            targetUser.setPassword(user.getPassword());

            String realname = user.getRealname() == null ? "" : user.getRealname();
            targetUser.setRealname(realname);

            String tel = user.getTelephone() == null ? "" : user.getTelephone();
            targetUser.setTelephone(tel);

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("roleName", user.getRoleName());
            targetUser.setRole(baseDaoImpl.getObjectsByFields(Role.class, queryParams).get(0));

            queryParams.remove("roleName");
            queryParams.put("name", user.getDepartmentName());
            targetUser.setDepartment(baseDaoImpl.getObjectsByFields(Department.class, queryParams).get(0));

            obj = baseDaoImpl.saveObject(targetUser);


        } catch (Exception e) {

            logger.error("更新用户信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        if (obj == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.ERROR);

        } else {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        }

        return result;

    }

    @Override
    public Result deleteUsers(Integer[] usersId) {

        Result result = new Result();

        int outcome = 0;

        try {

            outcome = userDaoImpl.deleteUsers(usersId);

        } catch (Exception e) {

            logger.error("删除用户信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

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


    @Override
    public User getUserById(Integer userId) {

        List<User> userList;

        User user;

        try {

            userList = userDaoImpl.getUserById(userId);

            if (userList == null || userList.size() == 0) {

                return null;
            }

            user = userList.get(0);

        } catch (Exception e) {

            logger.error("获取用户信息出现异常");
            e.printStackTrace();

            return null;

        }


        return user;

    }


}
