package com.harry.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order implements Serializable {

    public static final Integer OBLIGATION = 1;    //   "待付款"
    public static final Integer DONE = 2;              //   "已完成"
    public static final Integer CANCELED = 3;       //   "取消"

    private Integer id;
    private Integer userId;
    private Integer bookingUserId;
    private Integer houseId;
    private Integer isValid;
    private Date createDate;
    private Date dealDate;
    private Integer status;

    private Set<OrderDetail> orderDetails = new HashSet<>();


    public Order() {


    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(Integer bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookingUserId=" + bookingUserId +
                ", houseId=" + houseId +
                ", isValid=" + isValid +
                ", createDate=" + createDate +
                ", dealDate=" + dealDate +
                ", status=" + status +
                '}';
    }
}
