package com.harry.dao;

import com.harry.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    public List <User> getUsersByLimit (Map<String, Object> queryParams ) throws Exception ;

    public List <User> getUserByName ( String username ) throws  Exception;

    public int  deleteUsers ( Integer [] usersId ) throws Exception;

    public List<User> getUserById ( Integer userId ) throws Exception;


}
