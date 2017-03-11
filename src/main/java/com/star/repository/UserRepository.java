package com.star.repository;

import com.star.domain.User;

import java.util.List;

/**
 * Created by hp on 2017/3/10.
 */
public interface UserRepository {
    public User addUser(User user);
    public void deleteUser(Long id);
    public void updateUser(User user);
    public User findByUsername(String username);
    public List findAll();
}
