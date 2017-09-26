package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.data.Data;
import com.harry.data.Result;
import com.harry.entity.Company;
import com.harry.service.CompanyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    BaseDao baseDaoImpl;


    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    @Override
    public Result getCompanyInfo() {

        Result result = new Result();
        Data data = new Data();

        Company companyInfo;

        try {

            companyInfo = baseDaoImpl.getObjectById(Company.class, 1);

        } catch (Exception e) {

            logger.error("获取公司信息异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (companyInfo != null) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

            data.setE(companyInfo);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }

        result.setData(data);

        return result;
    }


}
