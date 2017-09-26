package com.harry.service;


import com.harry.data.Data;
import com.harry.data.Messager;
import com.harry.data.Result;
import com.harry.entity.House;
import com.harry.entity.User;

import java.io.File;
import java.util.Map;

public interface HouseService {


    public Result saveHouse(User currentUser, House house, File imageMain, String imageMainContentType,
                            File[] imagesDetail, String[] imagesDetailContentType, String imgSavePath);


    public Result updateHouse(House house, File imageMain, String imageMainFileName,
                              File[] imagesDetail, String[] imagesDetailFileName, String imgSavePath);


    public Result issueHouses(Integer[] housesId);


    public Result unShelveHouses(Integer[] housesId);


    public Result delHouses(Integer[] housesId);


    public Result getHouses(Messager messager);


    public Map<String, Object> getHousesByLimit(Map<String, Object> queryParams);


    public Result getHouseTypes();


}
