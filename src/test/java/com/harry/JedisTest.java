package com.harry;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class JedisTest {


    @Test
    public void test() {

        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //jedis.set("num","99999");

        // System.out.println( jedis.get("new") );

        jedis.hset("user:1", "name", "tom");

        System.out.println(jedis.hget("user:1", "name"));


        jedis.rpush("mylist", "1", "2", "3");

        List<String> mylist = jedis.lrange("mylist", 0, 2);


        System.out.println(mylist);


        jedis.sadd("myset", "ddd", "fff", "ccc");

        Set<String> myset = jedis.smembers("myset");

        System.out.println(myset);

        jedis.zadd("user:2 name", 100, "shana");

        System.out.println(jedis.zscore("user:2 name", "shana"));

        Set<String> keys = jedis.keys("*");

        System.out.println(keys);


    }


    @Test
    public void test2() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);

        Jedis jedis = jedisPool.getResource();

        System.out.println(jedis.get("new"));


    }

}
