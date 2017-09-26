package com.harry.dao.impl;


import com.harry.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository

public class BaseDaoImpl implements BaseDao {


    @Resource
    SessionFactory sessionFactory;


    public <T> Object saveObject(T t) throws Exception {

             /*
             *  这里不能使用openSession()获取连接，因为使用openSession()获取的连接不是
             *  由spring托管的dataSource产生的连接，不包含事务
             *
             *  openSession()相当于是在线程池之外开启新连接，必须手动提交事务和手动关闭
             *
             *  配置事务之后不需要手动关闭连接，因为事务提交之后会自动关闭session
             *
             * */

        Session session = sessionFactory.getCurrentSession();

        Object obj = session.merge(t);

        return obj;

    }


    public <T> void saveObjects(List<T> tList) throws Exception {

        Session session = sessionFactory.getCurrentSession();

        for (T t : tList) {

            session.merge(t);
        }

    }


    public <T> T getObjectById(Class<T> clazz, int id) throws Exception {

        Session session = sessionFactory.getCurrentSession();

        T t = session.get(clazz, id);

        return t;

    }


    public <T> List<T> getObjects(Class<T> clazz) throws Exception {

        Session session = sessionFactory.getCurrentSession();

        String hql = "select t from " + clazz.getSimpleName() + " t ";

        Query query = session.createQuery(hql);

        List<T> resultList = query.getResultList();

        return resultList;

    }


    public <T> List<T> getObjectsByFields(Class<T> clazz, Map<String, Object> fieldsMap) throws Exception {

        Session session = sessionFactory.getCurrentSession();

        String hql = "select t from " + clazz.getSimpleName() + " t where ";

        Set<String> fields = fieldsMap.keySet();

        Iterator<String> iterator = fields.iterator();

        while (iterator.hasNext()) {

            String field = iterator.next();

            hql += ("t.") + field + " = ? and ";
        }

        hql = hql.substring(0, hql.lastIndexOf("and"));  // 截断最后的"and"

        Query query = session.createQuery(hql);

        Iterator<String> iterator2 = fields.iterator();

        int i = 0;

        while (iterator2.hasNext()) {

            query.setParameter(i++, fieldsMap.get(iterator2.next()));
        }

        List resultList = query.getResultList();

        return resultList;

    }


    public <T> int updateObjectById(Class<T> clazz, int id, String field, Object value) throws Exception {

        Session session = sessionFactory.getCurrentSession();

        String hql = "update " + clazz.getSimpleName() + " as t set t." + field + " = ? where t.id = ?";

        Query query = session.createQuery(hql);

        query.setParameter(0, value);
        query.setParameter(1, id);

        int i = query.executeUpdate();

        return i;

    }


    public <T> int batchUpdate(Class<T> clazz, List<String> fieldList, Map<Integer, List<Object>> valueMap) throws Exception {

        int count = 0;
        int i = 0;

        Session session = sessionFactory.getCurrentSession();

        String hql = "update " + clazz.getSimpleName() + " as t set ";

        Iterator<String> iterator = fieldList.iterator();

        while (iterator.hasNext()) {

            hql += (iterator.next() + " = ?, ");
        }

        hql = hql.substring(0, hql.lastIndexOf(","));  // 截断最后的“ , ”

        hql += " where t.id = ? ";

        Set<Integer> keySet = valueMap.keySet();
        Iterator<Integer> keyIterator = keySet.iterator();

        Query query = session.createQuery(hql);

        while (keyIterator.hasNext()) {

            Integer nextKey = keyIterator.next();

            List<Object> objectList = valueMap.get(nextKey);

            int k = 0;

            Iterator<Object> objectIterator = objectList.iterator();

            while (objectIterator.hasNext()) {

                query.setParameter(k++, objectIterator.next());
            }

            query.setParameter(k, nextKey);
            i = query.executeUpdate();
            count += i;

        }

        return count;

    }


}
