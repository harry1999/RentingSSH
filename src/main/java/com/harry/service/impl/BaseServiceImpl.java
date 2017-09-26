package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class BaseServiceImpl implements BaseService {


    @Resource
    BaseDao baseDaoImpl;

    private static Logger logger = LogManager.getLogger(BaseServiceImpl.class.getName());


    public <T> boolean saveObject(T t) {

        try {

            baseDaoImpl.saveObject(t);

        } catch (Exception e) {

            logger.error("保存对象出现异常");
            e.printStackTrace();

            return false;

        }

        return true;
    }


    public <T> boolean saveObjects(List<T> tList) {


        try {

            baseDaoImpl.saveObjects(tList);

        } catch (Exception e) {

            logger.error("保存对象出现异常");
            e.printStackTrace();

            return false;

        }

        return true;

    }


    public <T> T getObjectById(Class<T> clazz, int id) {

        T t = null;

        try {

            t = baseDaoImpl.getObjectById(clazz, id);

        } catch (Exception e) {

            logger.error("获取对象出现异常");
            e.printStackTrace();

            return null;
        }


        return t;
    }


    public <T> List<T> getObjects(Class<T> clazz) {

        List<T> list = null;

        try {

            list = baseDaoImpl.getObjects(clazz);

        } catch (Exception e) {

            logger.error("获取对象出现异常");
            e.printStackTrace();

            return null;
        }


        return list;
    }


    public <T> List<T> getObjectsByFields(Class<T> clazz, Map<String, Object> fieldsMap) {

        List<T> list = null;

        try {

            list = baseDaoImpl.getObjectsByFields(clazz, fieldsMap);

        } catch (Exception e) {

            logger.error("通过属性获取对象出现异常");
            e.printStackTrace();

            return null;
        }

        return list;
    }


    public <T> int updateObjectById(Class<T> clazz, int id, String field, Object value) {

        int flag = 0;

        try {

            flag = baseDaoImpl.updateObjectById(clazz, id, field, value);

        } catch (Exception e) {

            logger.error("更新对象出现异常");
            e.printStackTrace();

            return flag;
        }

        return flag;

    }


    public <T> int batchUpdate(Class<T> clazz, List<String> fieldList, Map<Integer, List<Object>> valueMap) {

        int flag = 0;

        try {

            flag = baseDaoImpl.batchUpdate(clazz, fieldList, valueMap);

        } catch (Exception e) {

            logger.error("批量更新出现异常");
            e.printStackTrace();

            return flag;
        }

        return flag;

    }



}
