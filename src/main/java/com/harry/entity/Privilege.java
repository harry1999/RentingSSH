package com.harry.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Privilege implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer privilegeId;
    private String privilegeName;
    private String privilegePath;
    private Integer parentId;
    private String privilegeDesc;
    private Integer isValid;
    private Integer isMenu;


    private Set<Privilege> subPrivileges = new HashSet<>();


    public Integer getPrivilegeId() {
        return privilegeId;
    }


    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }


    public String getPrivilegeName() {
        return privilegeName;
    }


    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }


    public String getPrivilegePath() {
        return privilegePath;
    }


    public void setPrivilegePath(String privilegePath) {
        this.privilegePath = privilegePath;
    }


    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public String getPrivilegeDesc() {
        return privilegeDesc;
    }


    public void setPrivilegeDesc(String privilegeDesc) {
        this.privilegeDesc = privilegeDesc;
    }


    public Integer getIsValid() {
        return isValid;
    }


    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Set<Privilege> getSubPrivileges() {
        return subPrivileges;
    }

    public void setSubPrivileges(Set<Privilege> subPrivileges) {
        this.subPrivileges = subPrivileges;
    }


}
