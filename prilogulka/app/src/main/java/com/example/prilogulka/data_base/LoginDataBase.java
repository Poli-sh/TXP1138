package com.example.prilogulka.data_base;

import com.example.prilogulka.data.User;

import java.util.List;

public interface LoginDataBase {

    String CLASS_TAG = "DATA_BASE_LOGIN";

    String DATA_BASE_QUERRY = "create table login\n" +
            "(\n" +
            "  id integer primary key autoincrement,\n" +
            "  email varchar(255) not null,\n" +
            "  password varchar(45) not null,\n" +
            "  email_check_code varchar(10) not null,\n" +
            "  is_email_checked int default '0' not null" +
            ");";

    String DATA_BASE_COLUMS = " (email, password, email_check_code, is_email_checked) ";
    String SELECT_ALL_QUERRY = "SELECT * FROM login";
    String DATA_BASE_NAME = "login";
    int DATA_BASE_VERSION = 10;


    public int getUsersCount(List<User> userList);
    public List<User> selectAll();
    public List<User> findUser (String column, String filter);
    public void insertUser (User user);
    public void dropTable();
    public void createTable();
}
