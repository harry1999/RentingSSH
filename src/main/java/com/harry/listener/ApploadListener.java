package com.harry.listener;

import com.harry.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApploadListener implements ServletContextListener {


    SessionFactory sessionFactory;

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();


    // 创建定时任务定时，清理超过一分钟没处理的订单

    private Runnable getOrderCheckingTask( ) {

        String hql = " from Order order where order.status = 1 and order.isValid = 1 and order.dealDate = null ";

        return

                // 用lambda表达式返回函数式接口的对象

                () ->  {

                Session session = sessionFactory.openSession();

                Transaction transaction = session.beginTransaction();

                    Query query = session.createQuery( hql );

                     List <Order> orderList = query.list();

                    if ( orderList.size() != 0 ){

                        for ( Order order : orderList ) {

                        long interval =  ( new Date().getTime() - order.getCreateDate().getTime() ) / 1000 ;

                        if ( interval > 60 ) {

                            order.setStatus( Order.CANCELED );

                            session.merge( order );

                        }

                    }

                }

                    transaction.commit();

                    session.close();

            };

    }


    private void executeTask(Runnable runnable, int initDelay, int period) {

        service.scheduleAtFixedRate(runnable, initDelay, period, TimeUnit.SECONDS);

    }



    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        sessionFactory = (SessionFactory)wac.getBean("sessionFactory");

        // 在容器启动后开始执行定时任务

         executeTask(getOrderCheckingTask(), 5*60, 2*60);

    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        service.shutdown();

    }


}
