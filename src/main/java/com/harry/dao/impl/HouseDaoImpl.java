package com.harry.dao.impl;


import com.harry.dao.HouseDao;
import com.harry.data.Data;
import com.harry.data.Messager;
import com.harry.data.Page;
import com.harry.entity.House;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class HouseDaoImpl implements HouseDao {


    @Resource
    SessionFactory sessionFactory;


    public Data getHouses(Messager messager) throws Exception {

        // 每一页展示固定5条房屋信息

        Data data = new Data();
        Page page = new Page();

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = this.createHql(messager);

        Query query = currentSession.createQuery(hql);

        //  每次查询结果的总页数，要分整除5和不是整除5两种情况
        page.setTotalPage((query.list().size() % 5) == 0 ? query.list().size() / 5 : query.list().size() / 5 + 1);

        query.setMaxResults(5);

        if (messager.getIndex() != null) {

            query.setFirstResult((messager.getIndex() - 1) * 5);

        }

        List<House> houseList = query.list();

        data.setE(page);
        data.setList(houseList);

        return data;

    }


    @Override
    public List<House> getHousesByLimit(Map<String, Object> queryParams) {

        String hql = " from  House  house where house.isValid = 1 ";

        if (queryParams.get("houseTitle") != null) {

            hql += " and  house.title like '%" + queryParams.get("houseTitle") + "%' ";

        }


        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        if (queryParams.get("start") != null && queryParams.get("limit") != null) {

            query.setFirstResult((Integer) queryParams.get("start"));
            query.setMaxResults((Integer) queryParams.get("limit"));

        }

        List<House> houseList = query.list();

        return houseList;

    }


    @Override
    public int issueHouses(Integer[] housesId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update House house set house.issued = 1 where house.id in ( :array ) ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList("array", housesId);

        int outcome = query.executeUpdate();

        return outcome;

    }


    @Override
    public int unShelveHouses(Integer[] housesId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update House house set house.issued = 0 where house.id in ( :array ) ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList("array", housesId);

        int outcome = query.executeUpdate();

        return outcome;

    }


    @Override
    public int delHouses(Integer[] housesId) throws Exception {

        Session currentSession = sessionFactory.getCurrentSession();

        String hql = " update House house set house.isValid = 0 where house.id in ( :array ) ";

        Query query = currentSession.createQuery(hql);

        query.setParameterList("array", housesId);

        int outcome = query.executeUpdate();

        return outcome;

    }


    private String createHql(Messager messager) {

        String hql = " from  House house where ";

        String tempHql = "";

        // 如果用户使用了搜索，则按照模糊查询拼接搜索条件

        tempHql += messager.getTitle() != null ?

                ("( house.title like '%" + messager.getTitle() + "%' or " +
                        " house.description like '%" + messager.getTitle() + "%' or " +
                        " house.subDistrict.name like '%" + messager.getTitle() + "%' or " +
                        " house.houseType.type like '%" + messager.getTitle() + "%' ) ") : "";

        // 如果用户使用了价格过滤，则拼接价格过滤语句

        if (messager.getPriceRange() != null && messager.getPriceRange() != 0) {

            int priceRange = messager.getPriceRange();

            switch (priceRange) {


                case 1:
                    tempHql += " and house.price < 1000 ";
                    break;

                case 2:
                    tempHql += " and ( house.price >= 1000 and house.price <= 2000 )";
                    break;

                case 3:
                    tempHql += " and house.price > 2000 ";
                    break;
            }

        }


        // 如果用户使用了房屋面积过滤，则拼接面积过滤语句

        if (messager.getAreaRange() != null && messager.getAreaRange() != 0) {

            int areaRange = messager.getAreaRange();

            switch (areaRange) {


                case 1:
                    tempHql += " and house.area < 50 ";
                    break;

                case 2:
                    tempHql += " and ( house.area >= 50 and house.area <= 100 )";
                    break;

                case 3:
                    tempHql += " and house.area > 100 ";
                    break;

            }

        }

        // 如果用户选择了房型，则拼接房型过滤条件

        if (messager.getType() != null && messager.getType() != 0) {

            int houseTypeId = messager.getType();

            tempHql += (" and house.houseType.id = " + houseTypeId);

        }

        // 如果用户选择了街道，则拼接街道过滤条件

        if (messager.getStreet() != null && messager.getStreet() != 0) {

            int subDistrictId = messager.getStreet();

            tempHql += (" and house.subDistrict.id = " + subDistrictId);

        }

        hql = tempHql.equals("") ? hql + " house.isValid = 1 and house.issued = 1  " : hql + tempHql + " and house.isValid = 1 and house.issued = 1  ";

        return hql;

    }


}
