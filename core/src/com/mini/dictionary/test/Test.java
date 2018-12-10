package com.mini.dictionary.test;

import com.mini.dictionary.dao.EnglishChineseDictionaryDaoInf;
import com.mini.dictionary.entity.EnglishChineseDictionary;
import com.mini.dictionary.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SqlSession ss = MyBatisUtil.getSsf().openSession();
        EnglishChineseDictionaryDaoInf Ecddi = ss.getMapper(EnglishChineseDictionaryDaoInf.class);
        List<EnglishChineseDictionary> elist = Ecddi.getAllECD();
        System.out.println(elist);
        ss.close();
    }
}
