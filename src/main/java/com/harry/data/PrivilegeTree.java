package com.harry.data;


import java.io.Serializable;
import java.util.List;

public class PrivilegeTree implements Serializable {

    private Integer id;
    private String text;
    private Integer isMenu;
    private Boolean checked = false; //是否选中
    private List<PrivilegeTree> children; //子节点


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<PrivilegeTree> getChildren() {
        return children;
    }

    public void setChildren(List<PrivilegeTree> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "PrivilegeTree{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }


}
