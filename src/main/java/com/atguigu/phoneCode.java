package com.atguigu;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;

public class phoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        veritfy("198372355862");
        test("198372355862");
    }
    //3 验证码校验
    public static void test(String tel){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的验证码");
        String code = scanner.next();
        scanner.close();
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String codekey="code"+tel+"code";
        String rediscode = jedis.get(codekey);
        if(rediscode.equals(code)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
        jedis.close();

    }
    //2 每个手机每天只能发送三次，设置过期时间，将验证码放入到redis中
    public static  void veritfy(String tel){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String telkey="tel"+tel+":code";
        String codekey="code"+tel+"code";

        //每个手机每天只能发送三次
        String s = jedis.get(telkey);
        if(s==null){
            //第一次放入
            jedis.setex(telkey,24*60*60,"1");
        }else if(Integer.parseInt(s)<=2){
            //发送次数加1
            jedis.incr(telkey);
        }else{
            System.out.println("每个手机每天最多只能发送三次");
            jedis.close();
        }

        jedis.setex(codekey,60,getcode());
        System.out.println("您的验证码是:"+jedis.get(codekey));
        jedis.close();

    }
    //1 生成6位验证码
    public static String  getcode(){
        Random random = new Random();
        String vertifycode="";
        for(int i=0;i<6;i++){
            int code = random.nextInt(10);
            vertifycode=vertifycode+code;
        }
        return vertifycode;
    }
}
