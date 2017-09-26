package com.harry.entity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class District implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String name;


    Set<SubDistrict> subDistricts = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<SubDistrict> getSubDistricts() {
        return subDistricts;
    }

    public void setSubDistricts(Set<SubDistrict> subDistricts) {
        this.subDistricts = subDistricts;
    }


    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subDistricts=" + subDistricts +
                '}';
    }



}
