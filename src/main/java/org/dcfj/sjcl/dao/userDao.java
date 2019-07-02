package org.dcfj.sjcl.dao;


import org.apache.ibatis.annotations.Mapper;
import org.dcfj.sjcl.model.User;

import java.util.List;

@Mapper
public interface userDao {

    List<User> getUser();

}
