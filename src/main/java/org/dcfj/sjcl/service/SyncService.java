package org.dcfj.sjcl.service;

import org.dcfj.sjcl.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SyncService {

    //查询数据
    List<User> getUser();

    //同步数据
    Map<String,Object> asyncData(List<User> list);
}
