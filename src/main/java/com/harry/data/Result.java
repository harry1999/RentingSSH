package com.harry.data;

import java.io.Serializable;

public class Result<T, E> implements Serializable {


    /**
     *
     */

    private static final long serialVersionUID = 1L;


    public static final int SUCCESS = 1;
    public static final int FAILED = 2;

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String NETWORK_BUSY = "NETWORK_BUSY";
    public static final String USER_EXITS = "USER_EXITS";
    public static final String USER_NOT_EXITS = "USER_NOT_EXITS";
    public static final String LOGINED = "LOGINED";
    public static final String NOT_LOGINED = "NOT_LOGINED";
    public static final String USERNAME_OR_PWD_INVALID = "USERNAME_OR_PWD_INVALID";
    public static final String EMPTY = "EMPTY";
    public static final String LOCKED = "LOCKED";
    public static final String ROLE_INVALID = "ROLE_INVALID";
    public static final String PARAMETER_INVALID = "PARAMETER_INVALID";


    private int status;
    private String msg;
    private Data<T, E> data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data<T, E> getData() {
        return data;
    }

    public void setData(Data<T, E> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


}
