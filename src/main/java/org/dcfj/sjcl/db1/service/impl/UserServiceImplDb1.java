package org.dcfj.sjcl.db1.service.impl;

import org.dcfj.sjcl.db1.dao.userDaoDb1;
import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.db1.service.userServiceDb1;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService1")
public class UserServiceImplDb1 implements userServiceDb1 {

    @Resource
    private userDaoDb1 userDao1;

    @Override
    public List<User> getUser(){
        return userDao1.getUser();
    }

}
