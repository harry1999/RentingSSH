package com.harry.service.impl;


import com.harry.dao.DeptDao;
import com.harry.data.Data;
import com.harry.data.Result;
import com.harry.entity.Department;
import com.harry.service.DeptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {


    @Resource
    DeptDao deptDaoImpl;

    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    public Result getDepartments(Integer parentId) {

        Result result = new Result();

        List<Department> departmentList;

        try {

            departmentList = deptDaoImpl.getDepartments(parentId);

        } catch (Exception e) {

            logger.error("获取部门信息异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (departmentList == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }

        Data data = new Data();
        data.setList(departmentList);

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);
        result.setData(data);

        return result;

    }


}
