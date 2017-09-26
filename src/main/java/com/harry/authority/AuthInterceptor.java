package com.harry.authority;

import com.harry.dao.BaseDao;
import com.harry.entity.Privilege;
import com.harry.entity.RolePrivilege;
import com.harry.entity.User;
import com.harry.utils.SetToListUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Component
public class AuthInterceptor extends AbstractInterceptor {


    @Resource
    BaseDao baseDaoImpl;


    private List <String> freePrivilegeList = new ArrayList<>();

    private List <String> authPrivilegeList = new ArrayList<>();

    private List <String> extraPrivilegeList = new ArrayList<>();

    @Override
    public void init() {

        initFreePrivilegeList();

        initAuthPrivilegeList();

        initExtraPrivilegeList();

    }



    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {


        HttpServletRequest request = ServletActionContext.getRequest();
        String requestURL = request.getRequestURL().toString();


        // 如果请求的是不需要验证的action，则直接放行

        for ( String string : freePrivilegeList)    {

            if ( requestURL.contains( string) ) {

                String result = actionInvocation.invoke();

                return result;

            }

        }



        //  以下拦截的action都需要用户登陆后进行验证，所以对未登录用户的请求进行拦截

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        if ( loginUser == null ) {

            return "input";

        }


        // 如果用户不是租房用户且请求的是额外的action，则放行

        if ( loginUser.getRole().getId() != 3 ) {

            for ( String string :  extraPrivilegeList ) {

                if (requestURL.contains( string )) {

                    String result = actionInvocation.invoke();

                    return result;

                }
            }
        }


         // 验证用户所属角色是否具有action对应的权限

        List<String> userPrivilegeList = this.getUserPrivilegeList(loginUser);

                    for ( String str : userPrivilegeList ) {

                        if ( requestURL.contains(str) ) {

                            String result = actionInvocation.invoke();

                            return result;

                        }
                    }


        return "input";

    }



    private void initFreePrivilegeList () {

        freePrivilegeList.add( "/user/login" );
        freePrivilegeList.add( "/user/logout" );
        freePrivilegeList.add( "/user/isLogin" );
        freePrivilegeList.add( "/user/register" );
        freePrivilegeList.add( "/user/checkUsername" );
        freePrivilegeList.add( "/user/checkRememberMe" );
        freePrivilegeList.add( "/common/getHouses" );
        freePrivilegeList.add( "/common/getDistricts" );
        freePrivilegeList.add( "/common/getHouseTypes" );
        freePrivilegeList.add( "/common/getCompanyInfo" );
        freePrivilegeList.add( "/business/reserveHouse" );
        freePrivilegeList.add( "/business/comfirmReserve" );

    }



    private void initExtraPrivilegeList () {

        extraPrivilegeList.add( "/user/getUserPrivileges" );
        extraPrivilegeList.add( "/user/loadUsers" );
        extraPrivilegeList.add( "/user/getRoles" );
        extraPrivilegeList.add( "/user/getDepts" );
        extraPrivilegeList.add( "/common/loadHouses" );
        extraPrivilegeList.add( "/common/loadRoles" );
        extraPrivilegeList.add( "/common/checkRolesCorrelation" );

    }


    private void initAuthPrivilegeList  () {

        try {

            List<Privilege> privilegeList = baseDaoImpl.getObjects(Privilege.class);

            for (Privilege privilege : privilegeList) {

                authPrivilegeList.add(privilege.getPrivilegePath());

            }

        }catch ( Exception e) {


        }

    }


    private List <String> getUserPrivilegeList ( User currentUser ) {

        List <String> userPrivilegeList = new ArrayList<>();

        List<RolePrivilege> rolePrivilegeList = SetToListUtil.setToList( currentUser.getRole().getRolePrivileges() );

        for ( RolePrivilege rolePrivilege : rolePrivilegeList  ) {

            userPrivilegeList.add( rolePrivilege.getPrivilege().getPrivilegePath() );

        }

        return userPrivilegeList;

    }




}
