package com.harry.action;


import com.harry.data.Data;
import com.harry.data.PrivilegeTree;
import com.harry.data.Result;
import com.harry.entity.Privilege;
import com.harry.entity.RolePrivilege;
import com.harry.entity.User;
import com.harry.service.*;
import com.harry.service.impl.UserServiceImpl;
import com.harry.utils.EncryptUtil;
import com.harry.utils.SetToListUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component

public class UserAction extends ActionSupport {


    private User user;
    private Result result;

    private int page;
    private int rows;
    private String searchUsername;

    private Map<String, Object> usersInfo;

    private Integer[] usersId;

    private Integer orderId;


    @Resource
    BaseService baseServiceImpl;

    @Resource
    UserService userServiceImpl;

    @Resource
    RoleService roleServiceImpl;

    @Resource
    DeptService deptServiceImpl;

    @Resource
    OrderService orderServiceImpl;


    private static Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());



    // 处理登陆请求的action

    public String onLogin() {

        result = new Result();
        Data data = new Data();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", user.getUsername());
        params.put("password", EncryptUtil.encode(user.getPassword()));
        params.put("isValid", 1);

        List<User> userList = baseServiceImpl.getObjectsByFields(User.class, params);

        if (userList == null || userList.size() == 0) {

            params.put("password", user.getPassword());
            userList = baseServiceImpl.getObjectsByFields(User.class, params);

        }


        if (userList != null && userList.size() != 0) {

            User loginUser = userList.get(0);

            data.setE(loginUser.getRole().getId());

            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("loginUser", loginUser);


            // 如果用户选择了记住密码，则把用户的信息放在cookie中

            if (user.getIsChecked().equals("on")) {

                Cookie cookieUsername = new Cookie("username", loginUser.getUsername());
                Cookie cookiePwd = new Cookie("password", loginUser.getPassword());

                cookieUsername.setMaxAge(60 * 60 * 24 * 7);
                cookiePwd.setMaxAge(60 * 60 * 24 * 7);

                HttpServletResponse response = ServletActionContext.getResponse();
                response.addCookie(cookieUsername);
                response.addCookie(cookiePwd);

            }

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.USER_EXITS);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.USERNAME_OR_PWD_INVALID);

        }

        result.setData(data);

        return "success";

    }


    // 登录页面检查是否勾选记住密码


    public String checkRememberMe() {

        result = new Result();
        Data data = new Data();

        User user = new User();

        HttpServletRequest request = ServletActionContext.getRequest();
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("username")) {

                user.setUsername(cookie.getValue());
            }

            if (cookie.getName().equals("password")) {

                user.setPassword(cookie.getValue());

            }

        }


        if (user.getUsername() != null && user.getPassword() != null) {

            data.setE(user);

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NOT_FOUND);

        }

        result.setData(data);

        return "success";

    }


    // 检查用户是否已经登录

    public String isLogin() {

        result = new Result();
        Data data = new Data();

        HttpSession session = ServletActionContext.getRequest().getSession();

        Object loginUser = session.getAttribute("loginUser");

        if (loginUser != null) {

            data.setE(loginUser);

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.LOGINED);

        } else {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NOT_LOGINED);

        }

        result.setData(data);

        return "success";

    }


    //  用户退出登陆的请求

    public String onLogout() {

        HttpSession session = ServletActionContext.getRequest().getSession();

        session.removeAttribute("loginUser");

        result.setStatus(Result.SUCCESS);
        result.setMsg(Result.OK);

        return "success";

    }


    public String getMe() {

        result = new Result();

        HttpSession session = ServletActionContext.getRequest().getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        User user = userServiceImpl.getUserById(loginUser.getId());

        if (user == null) {

            result.setStatus(Result.FAILED);
            result.setMsg(Result.NETWORK_BUSY);

        } else {

            session.setAttribute("loginUser", user);

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);

        }

        return "success";
    }


    public String onCreateUser() {

        result = userServiceImpl.createUser(user);

        return "success";

    }


    public String onRegister() {

        result = userServiceImpl.register(user);

        return "success";
    }


    public String onUpdateUser() {

        result = userServiceImpl.updateUserByUser(user);

        return "success";

    }


    public String onDeleteUser() {

        result = userServiceImpl.deleteUsers(usersId);

        return "success";

    }


    public String getUserByName() {

        result = userServiceImpl.getUserByName(user.getUsername());

        return "success";

    }


    public String loadUsers() {

        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("username", searchUsername);
        queryParams.put("start", (page - 1) * rows);
        queryParams.put("limit", rows);


        List<User> userList = userServiceImpl.getUsersByLimit(queryParams);


        // 为了在easyUI中展示角色和部门信息必须做以下处理

        if (userList != null && userList.size() != 0) {

            for (User user : userList) {

                user.setRoleName(user.getRole().getRoleName());
                user.setDepartmentName(user.getDepartment().getName());

            }
        }


        queryParams.remove("start");
        queryParams.remove("limit");
        int userCount = userServiceImpl.getUsersByLimit(queryParams).size();


        usersInfo = new HashMap<>();
        usersInfo.put("total", userCount);
        usersInfo.put("rows", userList);

        return "success";

    }


    public String getRoles() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {

            return "input";

        }

        result = roleServiceImpl.getRoles(loginUser.getRole().getId());

        return "success";


    }


    public String getDepts() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {

            return "input";

        }

        result = deptServiceImpl.getDepartments(loginUser.getDepartment().getDeptno());

        return "success";

    }


    public String onDeleteOrder() {

        result = orderServiceImpl.deleteOrderById(orderId);

        return "success";

    }


    public String getUserPrivileges() {

        result = new Result();
        Data data = new Data();

        HttpSession session = ServletActionContext.getRequest().getSession();

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {

            Set<RolePrivilege> rolePrivilegeSet = loginUser.getRole().getRolePrivileges();

            List<RolePrivilege> rolePrivilegeList = SetToListUtil.setToList(rolePrivilegeSet);

            List<Privilege> privilegeList = new ArrayList<Privilege>();

            for (RolePrivilege rolePrivilege : rolePrivilegeList) {

                if (rolePrivilege.getPrivilege().getParentId() == 0) {

                    privilegeList.add(rolePrivilege.getPrivilege());

                }
            }

            List<PrivilegeTree> privilegeTreeList = getPrivilegeTreeList(privilegeList);

            data.setList(privilegeTreeList);

            result.setStatus(Result.SUCCESS);
            result.setMsg(Result.OK);
            result.setData(data);

        } else {

            // 在过滤器或者拦截器上进行控制，未登陆的用户不允许访问后台页面

        }


        return "success";

    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSearchUsername() {
        return searchUsername;
    }

    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
    }

    public Map<String, Object> getUsersInfo() {
        return usersInfo;
    }

    public void setUsersInfo(Map<String, Object> usersInfo) {
        this.usersInfo = usersInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer[] getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer[] usersId) {
        this.usersId = usersId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    private List<PrivilegeTree> getPrivilegeTreeList(List<Privilege> privileges) {

        List<PrivilegeTree> privilegeTreeList = new ArrayList<>();

        for (Privilege privilege : privileges) {

            PrivilegeTree privilegeTree = new PrivilegeTree();

            privilegeTree.setId(privilege.getPrivilegeId());
            privilegeTree.setText(privilege.getPrivilegeName());
            privilegeTree.setIsMenu(privilege.getIsMenu());

            // 这里privilege.getSubPrivileges()必须同时确认不为null而且元素个数不为0

            if (privilege.getSubPrivileges() != null && privilege.getSubPrivileges().size() != 0) {

                List<PrivilegeTree> subPrivilegeTreeList = getPrivilegeTreeList(SetToListUtil.setToList(privilege.getSubPrivileges()));
                privilegeTree.setChildren(subPrivilegeTreeList);

            }

            privilegeTreeList.add(privilegeTree);

        }

        return privilegeTreeList;

    }


}
