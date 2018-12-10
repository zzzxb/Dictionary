package com.mini.dictionary.test;

import com.mini.dictionary.entity.User;
import com.mini.dictionary.service.UserService;
import com.mini.dictionary.service.imp.UserServiceImp;

public class Test {
    public static void main(String[] args) {
        /**
         * 用JDBC连接数据库吧,我也搞不懂
         * 查找范例写好了你试试
         * dictionary下新建一个user表 两个字段 id name测试一下
         */
        UserService us = new UserServiceImp();
        for (User user : us.getName(1))
            System.out.println(user.getName());
    }
}
