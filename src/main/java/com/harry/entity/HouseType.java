package com.harry.entity;


import java.io.Serializable;

public class HouseType implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public static final String ONE_BEDROOM_ONE_LIVINGROOM = "一室一厅";
    public static final String TWO_BEDROOM_ONE_LIVINGROOM = "两室一厅";
    public static final String ONE_BEDROOM_TWO_LIVINGROOM = "一室两厅";
    public static final String TWO_BEDROOM_TWO_LIVINGROOM = "两室两厅";

    private Integer id;
    private String type;
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }


}
