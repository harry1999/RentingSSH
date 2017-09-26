package com.harry.dao;

import com.harry.entity.Department;

import java.util.List;

public interface DeptDao {

    public List <Department> getDepartments ( Integer parentId ) throws Exception;


}
