package com.mini.dictionary.util;

import com.badlogic.gdx.Gdx;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private final static SqlSessionFactory ssf;
    static{
        String resource = "core/assets/conf.xml";
        InputStream is = null;
        try{
            is = Resources.getResourceAsStream(resource);
        }catch(IOException io){
            System.out.println(io.getMessage());
        }
        ssf  = new SqlSessionFactoryBuilder().build(is);
    }
    public static SqlSessionFactory getSsf(){
        return ssf;
    }
}
