package com.atguigu;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;


public class jdesdemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }
    @Test
    public void test(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String set = jedis.set("name", "李磊");
        System.out.println(set);
        System.out.println(jedis.get("name"));
        System.out.println(jedis.keys("*"));
        jedis.mset("age", "18", "20", "40");
        List<String> name = jedis.mget("age");
        System.out.println(name);

    }
    @Test
    public void testlist(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.lpush("sex","男","女");
        System.out.println(jedis.lrange("sex",0,-1));

    }
    @Test
    public void testset(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.sadd("email","3441846689@qq.com");
        System.out.println(jedis.smembers("email"));

    }
    @Test
    public void testhash(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.hset("user1","age","12");
        System.out.println(jedis.hget("user1","age"));

    }
    @Test
    public void testzset(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.zadd("birth",12.5d,"java");
        jedis.zadd("birth",12d,"php");
        System.out.println(jedis.zrange("birth",0,-1));

    }
}
