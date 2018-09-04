package com.example.prilogulka.data;

import java.util.Random;

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
    private String birthday;
    private String registrationDate;
    private String lastDateOnline;


    public User(int id, String email, String password, String emailCheckCode, Boolean isEmailChecked,
    String name, String surname, String lastName, String city, String sex, String birthday,
    String registrationDate, String lastDateOnline) {
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
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.lastDateOnline = lastDateOnline;
    }

//  USER INFO DATA BASE
    public User(int id, String fullName,  String birthday, String sex, String city,
                String registrationDate,  String email, String password, String emailCheckCode,
                boolean isEmailChecked, String lastDateOnline) {


        this.id = id;
        if (fullName != null) {
            String buf[] = fullName.split(" ");
            this.name = buf[1];
            this.surname = buf[0];
            this.lastName = buf[2];
        } else {
            this.name = "";
            this.surname = "";
            this.lastName = "";
        }
        this.birthday = birthday;
        this.sex = sex;
        this.city = city;
        this.registrationDate = registrationDate;
        this.email = email;
        this.password = password;
        this.emailCheckCode = emailCheckCode;
        this.isEmailChecked = isEmailChecked;
        this.lastDateOnline = lastDateOnline;


    }

    public User(String email, String password, String emailCheckCode, Boolean isEmailChecked) {
        this.email = email;
        this.password = password;
        this.emailCheckCode = emailCheckCode;
        this.isEmailChecked = isEmailChecked;
}

    public User() {
        this.isEmailChecked = false;
        this.emailCheckCode = Math.abs(new Random().nextInt()) + "";
    }

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
        return name.isEmpty() ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname .isEmpty() ? "" : surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName.isEmpty() ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city.isEmpty() ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex.isEmpty() ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday.isEmpty() ? "" : birthday;
    }

    public void setBirthday(String birhday) {
        this.birthday = birhday;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastDateOnline() {
        return lastDateOnline;
    }

    public void setLastDateOnline(String lastDateOnline) {
        this.lastDateOnline = lastDateOnline;
    }
}


