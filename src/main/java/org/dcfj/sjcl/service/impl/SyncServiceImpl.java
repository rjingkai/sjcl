package org.dcfj.sjcl.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.dcfj.sjcl.db1.service.userServiceDb1;
import org.dcfj.sjcl.db2.service.userServiceDb2;
import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.service.SyncService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;

@Service
public class SyncServiceImpl implements SyncService {


    @Resource(name = "userService1")
    private userServiceDb1 userService1;

    @Resource(name = "userService2")
    private userServiceDb2 userService2;


    @Override
    public List<User> getUser(){
        return userService1.getUser();
    }


    @Override
    public Map<String,Object> asyncData(List<User> list){
        Map<String,Object> result = new HashMap<>();

        //需要同步的数量
        int addcount = list.size();

        //重复数据数量
        int cf = 0;

        try {
            //拼接已经存在的编号
            List<User> idslist = new ArrayList<User>();
            List<String> l = new ArrayList<>();
            for (int i = 0;i<list.size();i++){
                l.add(list.get(i).getId());

                User user = new User();
                BeanUtils.copyProperties(list.get(i),user);
                idslist.add(user);
            }

            //查询已经有的数据
            List<User> userList = userService2.getUser(l);
            if (CollectionUtils.isNotEmpty(userList)){
                //去除已经有的数据编号
                for (User ulist: userList) {
                    idslist.removeIf(user -> user.getId().equals(ulist.getId()));
                }
                addcount -=userList.size();
                cf +=userList.size();
            }

            if (CollectionUtils.isNotEmpty(idslist)){
                userService2.batchInsert(idslist);
            }
        }catch (Exception e){
            result.put("error",e.getMessage());
        }
        result.put("added", addcount);
        result.put("repeat", cf);
        return  result;
    }
}
















