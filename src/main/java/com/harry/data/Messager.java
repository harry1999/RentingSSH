package com.harry.data;

import java.io.Serializable;

public class Messager implements Serializable  {


    private String title;
    private Integer priceRange;
    private Integer areaRange;
    private Integer street;
    private Integer type;
    private Integer index;    // 存放前台请求的页码
    private Integer houseId;



    public Messager() {


    }


    public Messager (Integer houseId, String title, Integer index, Integer priceRange, Integer areaRange, Integer street, Integer type) {
        this.title = title;
        this.priceRange = priceRange;
        this.areaRange = areaRange;
        this.street = street;
        this.type = type;
        this.index = index;
        this.houseId = houseId;
    }


    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    public Integer getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(Integer areaRange) {
        this.areaRange = areaRange;
    }

    public Integer getStreet() {
        return street;
    }

    public void setStreet(Integer street) {
        this.street = street;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Messager{" +
                "title='" + title + '\'' +
                ", priceRange=" + priceRange +
                ", areaRange=" + areaRange +
                ", street=" + street +
                ", type=" + type +
                ", index=" + index +
                ", houseId=" + houseId +
                '}';
    }


}
