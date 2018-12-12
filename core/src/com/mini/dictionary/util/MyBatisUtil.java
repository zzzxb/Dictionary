package com.mini.dictionary.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil{
    private final static SqlSessionFactory ssf ;
    static{
        String resource = "conf.xml";
        InputStream is = null;
        try{
            is = Resources.getResourceAsStream(resource);
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        ssf = new SqlSessionFactoryBuilder().build(is);
    }
    public static SqlSessionFactory getSqlSessionFactory(){
       return ssf;
    }
}