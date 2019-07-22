package org.dcfj.sjcl.db2.service.impl;

import org.dcfj.sjcl.db2.dao.userDaoDb2;
import org.dcfj.sjcl.db2.service.userServiceDb2;
import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("userService2")
public class userServiceImplDb2 implements userServiceDb2 {
    @Resource
    private userDaoDb2 userDao2;

    @Override
    public List<User> getUser(){
        return userDao2.getUser();
    }
}




