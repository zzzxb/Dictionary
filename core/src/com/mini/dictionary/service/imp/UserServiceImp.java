package com.mini.dictionary.service.imp;

import com.mini.dictionary.dao.UserDao;
import com.mini.dictionary.dao.imp.UserImpl;
import com.mini.dictionary.entity.User;
import com.mini.dictionary.service.UserService;

import java.util.List;

public class UserServiceImp implements UserService {

    UserDao ud = new UserImpl();

    @Override
    public List<User> getName(int id) {
        return ud.getUserName(id);
    }
}
