package com.qiku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisDay01ApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void contextLoads() {
        //操作字符串
        ValueOperations ops = redisTemplate.opsForValue();
        //操作hash
        HashOperations hash = redisTemplate.opsForHash();
        //操作list
        ListOperations list = redisTemplate.opsForList();
        //操作set
        SetOperations set = redisTemplate.opsForSet();
        //操作zset
        ZSetOperations zSet = redisTemplate.opsForZSet();

        System.out.println(ops);
    }

    @Test
    void testString() {
        ValueOperations ops = redisTemplate.opsForValue();
        //set
        ops.set("name","张三");
        //get
        System.out.println("从redis中获取的值:"+ops.get("name"));
        //sexex
        ops.set("phoneCode","159753",5, TimeUnit.MINUTES);
        ops.setIfAbsent("name", "李四");//setnx
        System.out.println("不存在就设置:"+ops.get("name"));
        ops.setIfAbsent("name1", "孙悟空");
    }
    @Test
    void testHash(){
        HashOperations ops = redisTemplate.opsForHash();
        //set
        ops.put("map","username","admin");
        ops.put("map","password","123456");  //map  username admin password 123456
        //hget
        System.out.println(ops.get("map", "username"));//admin
        //hdel
        ops.delete("map", "username");//map  password 123456
        //hkeys
        ops.keys("map").forEach(System.out::println);//password
        //hvals
        ops.values("map").forEach(System.out::println);//123456

    }
    @Test
    void testList() {
        ListOperations ops = redisTemplate.opsForList();
/*        //单个加
        ops.leftPush("list", "张三");
        ops.leftPush("list", "李四");
        ops.leftPush("list", "王五");
        //批量加
        ops.leftPushAll("list1", "李树凯", "杜保华", "张顺雨");

        System.out.println(ops.range("list", 0, -1));
        System.out.println(ops.range("list1", 0, -1));
        //rpop
        ops.rightPop("list");
        //llen
        System.out.println(ops.size("list"));*/
        //brpop
        ops.rightPop("list", 20, TimeUnit.SECONDS);

    }
    @Test
    void testSet() {
        SetOperations ops = redisTemplate.opsForSet();
        //sadd
        ops.add("set", "三国演义", "红驴梦", "水浒传", "西游记");
        ops.add("set1", "西游记", "金瓶梅");
        //smembers
        System.out.println(ops.members("set"));
        System.out.println(ops.members("set1"));
        //scard
        System.out.println(ops.size("set"));
        //sinter
        System.out.println("set和set1集合的交集:"+ops.intersect("set", "set1"));
        //sunion
        System.out.println("set和set1集合的并集:"+ops.union("set", "set1"));
        //srem
        ops.remove("set1", "西游记", "金瓶梅");
    }
    @Test
    void testZset() {
        ZSetOperations ops = redisTemplate.opsForZSet();
        //zadd
        ops.add("zset1", "孙悟空", 99);
        ops.add("zset1", "猪八戒", 100);
        ops.add("zset1", "杜保华", 98);
        //zrange
        System.out.println(ops.range("zset1", 0, -1));
        //zincrby
        ops.incrementScore("zset1", "杜保华", 10);
        System.out.println(ops.range("zset1", 0, -1));
        //zrem
        ops.remove("zset1", "猪八戒","孙悟空");

        System.out.println(ops.range("zset1", 0, -1));
    }
    @Test
    void testKeys() {
        //keys pattern
 /*       System.out.println(redisTemplate.keys("*1"));
        //exists
        System.out.println(redisTemplate.hasKey("list1"));
        //type
        System.out.println(redisTemplate.type("list1"));
        //del
        redisTemplate.delete("set");
        //expire  设置key时间
        redisTemplate.expire("map", 5, TimeUnit.MINUTES);*/
        //ttl 获取存活时间
        System.out.println(redisTemplate.getExpire("map"));
    }


}
