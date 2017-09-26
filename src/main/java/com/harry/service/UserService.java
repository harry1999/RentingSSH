package com.harry.service;

import com.harry.data.Result;
import com.harry.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {


    public List<User> getUsersByLimit(Map<String, Object> queryParams);


    public Result createUser(User user);


    public Result register(User user);


    public Result getUserByName(String username);


    public Result updateUserByUser(User user);


    public Result deleteUsers(Integer[] usersId);


    public User getUserById(Integer userId);


}
