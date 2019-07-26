package org.dcfj.sjcl.db2.service;

import org.dcfj.sjcl.model.PcsBean;
import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

public interface userServiceDb2 {
    List<User> getUser(List<String> ids);
    void  batchInsert(List<User> list);

    List<PcsBean> getPcs(List<String> orgid);

    void batchInsertPcs(List<PcsBean> list);
}
