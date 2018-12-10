package com.mini.dictionary.dao;

import com.mini.dictionary.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUserName(int id);
}
