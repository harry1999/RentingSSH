package com.harry.authority;

import com.harry.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthFilter implements Filter {

    // 不需要认证的页面集合

    private  List <String> freePageList = new ArrayList<>();

    // 需要验证的前台页面集合

    private  List<String> authFontPageList = new ArrayList<>();

    // 需要认证的后台页面集合

    private List <String> authBackgroundPageList = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        initFreePageList();

        initFontPageList();

        initAuthBackgroundPageList();

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

      HttpServletRequest request =  (HttpServletRequest)servletRequest;
      HttpServletResponse response = (HttpServletResponse)servletResponse;


      // 获取应用程序的根路径

        String appPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() +
                request.getContextPath() +  "/";

        String requestURI = request.getRequestURI();


        // 如果请求的是静态资源，或者是首页，则放行

        if ( requestURI.endsWith(".js") || requestURI.endsWith(".css") || requestURI.endsWith("jpg") || requestURI.endsWith("png") ||
                requestURI.endsWith("gif") || requestURI.equals("/RentingSSH/") ) {

            filterChain.doFilter( request, response );

            return;

        }


        //  如果请求的是不需要认证的页面，如首页、房屋详情页面等，则放行

        for ( String string : freePageList)   {

            if ( requestURI.contains( string )) {

                filterChain.doFilter( request, response );

                return;

            }

        }


        //  如果请求的是需要认证的前台页面，则根据情况进行处理

        for ( String string : authFontPageList)   {

            if ( requestURI.contains( string )) {

                User loginUser = (User) request.getSession().getAttribute("loginUser");

                if ( loginUser == null ) {

                    response.sendRedirect(appPath + "pages/login.htm");

                    return;

                } else {

                    if ( loginUser.getRole().getId() != 3) {

                        response.sendRedirect(appPath + "pages/management/management.html");

                    } else {

                        filterChain.doFilter( request, response );

                        return;

                    }
                }
            }
        }



        //  如果请求的是需要认证的后台页面，则根据情况进行处理

        for ( String string : authBackgroundPageList)   {

            if ( requestURI.contains( string )) {

                User loginUser = (User) request.getSession().getAttribute("loginUser");

                if ( loginUser == null ) {

                    response.sendRedirect(appPath + "pages/index.htm");

                    return;

                } else {


                    if ( loginUser.getRole().getId() == 3) {

                        response.sendRedirect(appPath + "pages/index.htm");

                    } else {

                        filterChain.doFilter( request, response );

                        return;

                    }
                }
            }
        }

        filterChain.doFilter( request,  response );

    }


    @Override
    public void destroy() {

    }


    private void initFreePageList () {

        freePageList.add( "index.htm" );
        freePageList.add( "detail.htm" );
        freePageList.add( "login.htm" );
        freePageList.add( "register.htm" );
        freePageList.add( "404.jsp" );
        freePageList.add( "500.jsp" );

    }



    private void initFontPageList () {

        authFontPageList.add( "personal.jsp" );

    }


    private void initAuthBackgroundPageList() {

        authBackgroundPageList.add( "management.html" );

    }



}
