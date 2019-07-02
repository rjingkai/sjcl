package org.dcfj.sjcl.service.impl;

import org.dcfj.sjcl.dao.userDao;
import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userDao userDao;

    @Override
    public List<User> getUser(){
        return userDao.getUser();
    }

}
