package com.star.domain;

import javax.persistence.*;

/**
 * Created by hp on 2017/3/10.
 */
//@Table的catalog属性指定要使用的数据库名，不指定则使用dataSource中url指定的数据库，一般不设置
//schema 作用与catalog一致
@Entity
@Table(name="users")
public class User {
    private Long id;
    private String username;
    private String password;
    private String ROLE_USER;
    private int enabled;

    public User() {
    }

    public User(Long id, String username, String password, String role_user, int enabled) {
        this.id=id;
        this.username=username;
        this.password=password;
        this.ROLE_USER=role_user;
        this.enabled=enabled;
    }

    @Id
    @Column(name="id",nullable = false,insertable = true,updatable = true)
    public Long getId() {
        return id;
    }
    @Basic
    @Column(name = "username",nullable = false)
    public String getUsername() {
        return username;
    }
    @Basic
    @Column(name="password",nullable = false)
    public String getPassword() {
        return password;
    }
    @Basic
    @Column(name="ROLE_USER",nullable = false)
    public String getROLE_USER() {
        return ROLE_USER;
    }
    @Basic
    @Column
    public int getEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setROLE_USER(String ROLE_USER) {
        this.ROLE_USER = ROLE_USER;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
