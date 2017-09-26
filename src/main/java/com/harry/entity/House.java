package com.harry.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class House implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String title;
    private Double area;   // 房屋面积
    private Double price;
    private Double deposit;
    private String telephone;
    private String description;
    private Date issueDate;    // 房屋信息发布时间
    private Integer issued;
    private Integer isValid;

    private User user;   // 发布房屋信息的出租人
    private User bookingUser;  // 预租该房屋的人
    private SubDistrict subDistrict;
    private HouseType houseType;

    private Set<HouseImage> houseImages = new HashSet<>();

    // 定义以下两个变量是为了方便在easy ui中展示数据

    private String userName;
    private String bookingUserName;


    public House() {


    }


    public House(String title, Double area, Double price, String telephone,
                 String description, Date issueDate, Integer isValid) {
        super();
        this.title = title;
        this.area = area;
        this.price = price;
        this.telephone = telephone;
        this.description = description;
        this.issueDate = issueDate;
        this.isValid = isValid;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String tel) {
        this.telephone = tel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }


    public Integer getIssued() {
        return issued;
    }

    public void setIssued(Integer issued) {
        this.issued = issued;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public User getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(User bookingUser) {
        this.bookingUser = bookingUser;
    }

    public SubDistrict getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(SubDistrict subDistrict) {
        this.subDistrict = subDistrict;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseType houseType) {
        this.houseType = houseType;
    }


    public Set<HouseImage> getHouseImages() {
        return houseImages;
    }

    public void setHouseImages(Set<HouseImage> houseImages) {
        this.houseImages = houseImages;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookingUserName() {
        return bookingUserName;
    }

    public void setBookingUserName(String bookingUserName) {
        this.bookingUserName = bookingUserName;
    }


    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", area=" + area +
                ", price=" + price +
                ", deposit=" + deposit +
                ", telephone='" + telephone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
