package com.harry.service;

import java.util.List;
import java.util.Map;

public interface BaseService {


    public <T> boolean saveObject(T t);


    public <T> boolean saveObjects(List<T> tList);


    public <T> T getObjectById(Class<T> clazz, int id);


    public <T> List<T> getObjects(Class<T> clazz);


    public <T> List<T> getObjectsByFields(Class<T> clazz, Map<String, Object> fieldsMap);


    public <T> int updateObjectById(Class<T> clazz, int id, String field, Object value);


    public <T> int batchUpdate(Class<T> clazz, List<String> fieldList, Map<Integer, List<Object>> valueMap);


}
