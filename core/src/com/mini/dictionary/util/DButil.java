package com.mini.dictionary.util;

import java.sql.*;
import java.util.List;

public class DButil {
    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;

    public static ResultSet executeQuest(String sql, List<Object> list) {
        try {
            ps = getConnection().prepareStatement(sql);
            for (int i = 0; i < list.size(); i++)
                ps.setObject(i+1, list.get(i));
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /** 加载驱动连接数据库*/
    public static Connection getConnection() {
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dictionary?useSSL=false&serverTimezone=UTC",
                    "root","happy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void CloseAll() {
        try{
            if (rs != null)
                rs.close();
            if (ps != null)
                rs.close();
            if (conn != null)
                conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
