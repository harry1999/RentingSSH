package com.harry.action;

import com.harry.data.Result;
import com.harry.entity.User;
import com.harry.service.OrderService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Component
public class BusinessAction extends ActionSupport {

    @Resource
    OrderService orderServiceImpl;

    private Result result;

    private Integer houseId;

    private Integer tag;

    private Integer orderId;


    public String onReserveHouse() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User currentUser = (User) session.getAttribute("loginUser");

        if (currentUser.getRole().getId() != 3) {

            result = new Result();

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.ROLE_INVALID);

            return "success";

        }


        result = orderServiceImpl.reserveHouse(houseId, currentUser);

        return "success";

    }


    public String onComfirmReserve() {

        result = orderServiceImpl.ComfirmReserve(tag, orderId);

        return "success";

    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


}
