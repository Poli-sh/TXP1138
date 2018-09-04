package com.example.prilogulka.data;

public class User {
    private int id;
    private String email;
    private String password;
    private String emailCheckCode;
    private Boolean isEmailChecked;
    private String name;
    private String surname;
    private String lastName;
    private String city;
    private String sex;

    public User(int id, String email, String password, String emailCheckCode, Boolean isEmailChecked,
    String name, String surname, String lastName, String city, String sex) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.emailCheckCode = emailCheckCode;
        this.isEmailChecked = isEmailChecked;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.city = city;
        this.sex = sex;
    }

    public User(int id, String email, String password, String emailCheckCode, Boolean isEmailChecked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.emailCheckCode = emailCheckCode;
        this.isEmailChecked = isEmailChecked;
    }

    public User(String email, String password, String emailCheckCode, Boolean isEmailChecked) {
        this.email = email;
        this.password = password;
        this.emailCheckCode = emailCheckCode;
        this.isEmailChecked = isEmailChecked;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailCheckCode() {
        return emailCheckCode;
    }

    public void setEmailCheckCode(String emailCheckCode) {
        this.emailCheckCode = emailCheckCode;
    }

    public Boolean getEmailChecked() {
        return isEmailChecked;
    }

    public void setEmailChecked(Boolean emailChecked) {
        isEmailChecked = emailChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

