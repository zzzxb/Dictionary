package com.mini.dictionary.dao.imp;

import com.mini.dictionary.dao.UserDao;
import com.mini.dictionary.entity.User;
import com.mini.dictionary.util.DButil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements UserDao {
    @Override
    public List<User> getUserName(int id) {
        String sql = "SELECT * from user where id=?";
        List<Object> list = new ArrayList<Object>();
        List<User> user = new ArrayList<User>();
        list.add(id);
        ResultSet rs = DButil.executeQuest(sql, list);
        try {
            if (rs.next()) {
                User user1 = new User();
                user1.setId(rs.getInt(1));
                user1.setName(rs.getString(2));
                user.add(user1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DButil.CloseAll();
        }
        return user;
    }
}
