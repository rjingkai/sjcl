package org.dcfj.sjcl.db2.dao;


import org.dcfj.sjcl.model.PcsBean;
import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userDaoDb2 {

    List<User> getUser(List<String> ids);

    void batchInsert(List<User> list);

    List<PcsBean> getPcs(List<String> orgid);

    void batInsertPcs(List<PcsBean> list);


}
