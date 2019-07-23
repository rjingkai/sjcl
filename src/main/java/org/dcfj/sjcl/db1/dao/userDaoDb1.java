package org.dcfj.sjcl.db1.dao;


import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface userDaoDb1 {

    List<User> getUser();

}
