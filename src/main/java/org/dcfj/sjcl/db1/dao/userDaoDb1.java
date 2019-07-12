package org.dcfj.sjcl.db1.dao;


import org.apache.ibatis.annotations.Mapper;
import org.dcfj.sjcl.model.User;

import java.util.List;

@Mapper
public interface userDaoDb1 {

    List<User> getUser();

}
