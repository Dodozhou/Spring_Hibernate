package com.star.repository.impl;

import com.star.domain.User;
import com.star.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 2017/3/10.
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private SessionFactory sessionFactory;
    //使用@Inject注解注入sessionFactory
    @Inject
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    public Session currentSession(){
        return sessionFactory.getCurrentSession();
    }

    public User addUser(User user) {
        Serializable id=currentSession().save(user);
        return new User((Long)id,
                user.getUsername(),
                user.getPassword(),
                user.getROLE_USER(),
                user.getEnabled());
    }

    public void deleteUser(Long  id) {
        String hql="delete from User user where id="+id.toString();
        currentSession().createQuery(hql);
    }

    public void updateUser(User user) {
        currentSession().update(user);
    }

    public User findByUsername(String username) {
        return currentSession().find(User.class,username);
    }

    public List findAll() {
        String sql="from User user";
        return currentSession().createQuery(sql).list();

    }
}
