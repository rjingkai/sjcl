package org.dcfj.sjcl.db2.dao;


import org.apache.ibatis.annotations.Mapper;
import org.dcfj.sjcl.model.User;

import java.util.List;

@Mapper
public interface userDaoDb2 {

    List<User> getUser();

}
