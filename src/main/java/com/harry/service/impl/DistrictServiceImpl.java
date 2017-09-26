package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.data.Data;
import com.harry.data.Result;
import com.harry.entity.District;
import com.harry.entity.SubDistrict;
import com.harry.service.DistrictService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {


    @Resource
    BaseDao baseDaoImpl;

    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());



    @Override
    public Result getDistricts( Integer tag ) {

        Result result = new Result();
        Data data = new Data();

        List districtList = null;

        try {

            districtList = (tag == 1) ? baseDaoImpl.getObjects(District.class) : baseDaoImpl.getObjects(SubDistrict.class);

        } catch (Exception e) {

            logger.error("获取地区信息异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        if (districtList != null && districtList.size() != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

            data.setList(districtList);


        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }

        result.setData(data);

        return result;
    }

}
