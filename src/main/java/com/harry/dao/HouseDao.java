package com.harry.dao;

import com.harry.data.Data;
import com.harry.data.Messager;
import com.harry.entity.House;

import java.util.List;
import java.util.Map;

public interface HouseDao  {


    public Data getHouses (Messager messager) throws Exception;

    public List <House> getHousesByLimit ( Map<String, Object> queryParams ) throws Exception;

    public int issueHouses (Integer [] housesId ) throws Exception;

    public int unShelveHouses ( Integer [] housesId ) throws  Exception;

    public int delHouses ( Integer [] housesId ) throws Exception;


}
