package com.mini.dictionary.test;

import com.mini.dictionary.dao.EnglishChineseDictionaryDaoInf;
import com.mini.dictionary.entity.EnglishChineseDictionary;
import com.mini.dictionary.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SqlSession ss = MyBatisUtil.getSqlSessionFactory().openSession();
        EnglishChineseDictionaryDaoInf Ecddi = ss.getMapper(EnglishChineseDictionaryDaoInf.class);
        List<EnglishChineseDictionary> elist = Ecddi.getAllECD();
        for(EnglishChineseDictionary Ecd : elist){
            System.out.println(Ecd.getWordId()+" "+Ecd.getWordSpell()+" "+Ecd.getWordMeaning()+" "+Ecd.getWordType()+" "+Ecd.getWordSoundmark());
        }
    }
}
