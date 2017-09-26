package com.harry.dao;


import java.util.List;
import java.util.Map;

public interface BaseDao {


    public <T> Object saveObject(T t) throws Exception;


    public <T> void saveObjects(List<T> tList) throws Exception;


    public <T> T getObjectById(Class<T> clazz, int id) throws Exception;


    public <T> List<T> getObjects(Class<T> clazz) throws Exception;


    public <T> List<T> getObjectsByFields(Class<T> clazz, Map<String, Object> fieldsMap) throws  Exception;


    public <T> int updateObjectById(Class<T> clazz, int id, String field, Object value) throws Exception;


    public <T> int batchUpdate(Class<T> clazz, List<String> fieldList, Map<Integer, List<Object>> valueMap) throws Exception;


}
