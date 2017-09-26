package com.harry.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String roleName;
    private String roleDesc;
    private Integer isValid;

    private Role parentRole;
    private String parentRoleName;

    private Set<RolePrivilege> rolePrivileges = new HashSet<>();


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleDesc() {
        return roleDesc;
    }


    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }


    public Integer getIsValid() {
        return isValid;
    }


    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    public Role getParentRole() {
        return parentRole;
    }

    public void setParentRole(Role parentRole) {
        this.parentRole = parentRole;
    }


    public Set<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }


    public String getParentRoleName() {
        return parentRoleName;
    }

    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }


}
