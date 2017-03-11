package com.star.controller;

import com.star.domain.User;
import com.star.repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hp on 2017/3/10.
 */
@Controller
@Transactional
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/users")
    public String users_detail(Model model){
        model.addAttribute("userList",userRepository.findAll());
        return "userManage";
    }

    @RequestMapping(name = "/addUser",method = RequestMethod.GET)
    public String addForm(Model model){
        model.addAttribute("user",new User());
        return "addForm";
    }

    @RequestMapping(name = "/addUserPost",method = RequestMethod.POST)
    public String addUser(User user){
        userRepository.addUser(user);
        return "redirect:/users";
    }
    @RequestMapping(name ="/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userRepository.deleteUser(id);
        return "redirect:/users";
    }
}
