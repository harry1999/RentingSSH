package com.harry.service.impl;

import com.harry.dao.BaseDao;
import com.harry.dao.HouseDao;
import com.harry.data.Data;
import com.harry.data.Messager;
import com.harry.data.Result;
import com.harry.entity.*;
import com.harry.service.HouseService;
import com.harry.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HouseServiceImpl implements HouseService {


    @Resource
    BaseDao baseDaoImpl;


    @Resource
    HouseDao houseDaoImpl;


    private static Logger logger = LogManager.getLogger(CompanyServiceImpl.class.getName());


    public Result saveHouse(User currentUser, House house, File imageMain, String imageMainFileName,
                            File[] imagesDetail, String[] imagesDetailFileName, String imgSavePath) {

        Result result = new Result();

        if (imageMain != null) {

            Image imgMain = new Image();
            String imgMainName = new Date().getTime() + "-main" + FileUtil.getFileType(imageMainFileName);
            imgMain.setImagePath(imgMainName);
            imgMain.setIsValid( 1 );


            HouseImage houseImage = new HouseImage();
            houseImage.setHouse(house);
            houseImage.setImage(imgMain);
            houseImage.setType("main");

            house.getHouseImages().add(houseImage);


            try {

                FileUtils.copyFile(imageMain, new File(imgSavePath + imgMainName));

            } catch (IOException e) {

                logger.error("拷贝主图出现异常");
                e.printStackTrace();

                result.setStatus(Result.FAILED);
                result.setMsg(Result.NETWORK_BUSY);

                return result;

            }

        }


        if (imagesDetail != null) {

            for (int i = 0; i < imagesDetail.length; i++) {

                String imgDetailName = new Date().getTime() + "-detail" + FileUtil.getFileType(imagesDetailFileName[i]);

                Image image = new Image();
                image.setImagePath(imgDetailName);
                image.setIsValid( 1 );

                HouseImage houseImage2 = new HouseImage();
                houseImage2.setHouse(house);
                houseImage2.setImage(image);
                houseImage2.setType("detail");

                house.getHouseImages().add(houseImage2);


                File newFile = new File(imgSavePath + imgDetailName);

                try {

                    FileUtils.copyFile(imagesDetail[i], newFile);

                } catch (IOException e) {

                    logger.error("拷贝附图出现异常");
                    e.printStackTrace();

                    result.setStatus(Result.FAILED);
                    result.setMsg(Result.NETWORK_BUSY);

                    return result;
                }
            }

        }


        try {

            house.setUser(currentUser);
            house.setIsValid(1);
            house.setIssued(0);   // 默认issued==0，后台点击发布后，issued=1
            house.setIssueDate(new Date());
            house.setHouseType(baseDaoImpl.getObjectById(HouseType.class, house.getHouseType().getId()));
            house.setSubDistrict(baseDaoImpl.getObjectById(SubDistrict.class, house.getSubDistrict().getId()));

            baseDaoImpl.saveObject(house);

        } catch (Exception e) {

            logger.error("保存房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }


        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return result;

    }


    @Override
    public Result updateHouse(House houseToUpdate, File imageMain, String imageMainFileName, File[] imagesDetail, String[] imagesDetailFileName, String imgSavePath) {

        Result result = new Result();
        House house;

        try {

            house = baseDaoImpl.getObjectById(House.class, houseToUpdate.getId());

        } catch ( Exception e ) {

            logger.error("更新房屋信息，获取房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (imageMain != null) {

            Image imgMain = new Image();
            String imgMainName = new Date().getTime() + "-main" + FileUtil.getFileType(imageMainFileName);
            imgMain.setImagePath(imgMainName);
            imgMain.setIsValid( 1 );

            HouseImage houseImage = new HouseImage();
            houseImage.setHouse(house);
            houseImage.setImage(imgMain);
            houseImage.setType("main");

            house.getHouseImages().add(houseImage);


            try {

                FileUtils.copyFile(imageMain, new File(imgSavePath + imgMainName));

            } catch (IOException e) {

                logger.error("更新房屋信息，拷贝主图出现异常");
                e.printStackTrace();

                result.setStatus(Result.FAILED);
                result.setMsg(Result.NETWORK_BUSY);

                return result;

            }

        }


        if (imagesDetail != null) {

            for (int i = 0; i < imagesDetail.length; i++) {

                String imgDetailName = new Date().getTime() + "-detail" + FileUtil.getFileType(imagesDetailFileName[i]);

                Image image = new Image();
                image.setImagePath(imgDetailName);
                image.setIsValid( 1 );

                HouseImage houseImage2 = new HouseImage();
                houseImage2.setHouse(house);
                houseImage2.setImage(image);
                houseImage2.setType("detail");

                house.getHouseImages().add(houseImage2);

                File newFile = new File(imgSavePath + imgDetailName);

                try {

                    FileUtils.copyFile(imagesDetail[i], newFile);

                } catch (IOException e) {

                    logger.error("更新房屋信息，拷贝附图出现异常");
                    e.printStackTrace();

                    result.setStatus(Result.FAILED);
                    result.setMsg(Result.NETWORK_BUSY);

                    return result;
                }
            }

        }


        try {

            house.setTitle(houseToUpdate.getTitle());
            house.setArea(houseToUpdate.getArea());
            house.setPrice(houseToUpdate.getPrice());
            house.setDeposit(houseToUpdate.getDeposit());
            house.setTelephone(houseToUpdate.getTelephone());
            house.setDescription(houseToUpdate.getDescription());

            house.setHouseType(baseDaoImpl.getObjectById(HouseType.class, houseToUpdate.getHouseType().getId()));
            house.setSubDistrict(baseDaoImpl.getObjectById(SubDistrict.class, houseToUpdate.getSubDistrict().getId()));

            baseDaoImpl.saveObject(house);

        } catch (Exception e) {

            logger.error("更新房屋信息，保存房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return result;
    }


    @Override
    public Result issueHouses(Integer[] housesId) {

        Result result = new Result();

        int outcome = 0;

        try {

            outcome = houseDaoImpl.issueHouses(housesId);

        } catch (Exception e) {

            logger.error("发布房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }

        if (outcome != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        return result;


    }


    @Override
    public Result unShelveHouses(Integer[] housesId) {

        Result result = new Result();

        int outcome = 0;

        try {

            outcome = houseDaoImpl.unShelveHouses(housesId);

        } catch ( Exception e ) {

            logger.error("下架房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }


        if (outcome != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        return result;

    }


    @Override
    public Result delHouses(Integer[] housesId) {

        Result result = new Result();

        int outcome = 0;

        try {

            outcome = houseDaoImpl.delHouses(housesId);

        } catch ( Exception e ) {

            logger.error("删除房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;

        }


        if (outcome != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        }

        return result;
    }


    public Result getHouses(Messager messager) {

        Result result = new Result();
        Data data = new Data();

        if (messager.getHouseId() != null) {

            House house = null;

            try {

                house = baseDaoImpl.getObjectById(House.class, messager.getHouseId());

            } catch (Exception e) {

                logger.error("获取房屋信息出现异常");
                e.printStackTrace();

                result.setStatus(Result.FAILED);
                result.setMsg(Result.NETWORK_BUSY);

                return result;
            }


            if (house != null) {

                data.setE(house);

                result.setStatus(Result.SUCCESS);
                result.setMsg(Result.OK);
                result.setData(data);

            } else {

                result.setStatus(Result.FAILED);
                result.setMsg(Result.EMPTY);

            }

            return result;

        }


        try {

            data = houseDaoImpl.getHouses(messager);

        } catch (Exception e) {

            logger.error("获取房屋信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

            return result;
        }

        if (data != null && data.getList().size() != 0) {

            result.setData(data);
            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }


        return result;

    }


    @Override
    public Map<String, Object> getHousesByLimit(Map<String, Object> queryParams) {

        try {

            List<House> houseList = houseDaoImpl.getHousesByLimit(queryParams);

            if (houseList != null && houseList.size() != 0) {

                for (House house : houseList) {

                    house.setUserName(house.getUser().getUsername());
                    house.setBookingUserName(house.getBookingUser() != null ? house.getBookingUser().getUsername() : "未出租");

                }

            }

            queryParams.remove("start");
            queryParams.remove("limit");
            int houseCount = houseDaoImpl.getHousesByLimit(queryParams).size();

            Map<String, Object> housesInfo = new HashMap<>();
            housesInfo.put("total", houseCount);
            housesInfo.put("rows", houseList);

            return housesInfo;

        }catch ( Exception e ) {

            logger.error("翻页获取房屋信息出现异常");
            e.printStackTrace();

            return null;

        }

    }

    @Override
    public Result getHouseTypes() {

        Result result = new Result();
        Data data = new Data();

        List<HouseType> houseTypeList = null;

        try {

          houseTypeList =   baseDaoImpl.getObjects(HouseType.class);

        }catch ( Exception e ) {

            logger.error("获取房屋类型信息出现异常");
            e.printStackTrace();

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY );

            return result;

        }

        if (houseTypeList != null && houseTypeList.size() != 0) {

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

            data.setList(houseTypeList);
            result.setData(data);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.EMPTY);

        }

        return result;


    }


}
