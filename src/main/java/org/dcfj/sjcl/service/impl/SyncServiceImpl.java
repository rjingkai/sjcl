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


    /**
     * 查询需要同步的数据
     * @return
     */
    @Override
    public List<User> getUser(){
        return userService1.getUser();
    }


    /**
     * 同步数据操作
     * @param list 需要同步的数据集合
     * @return 返回同步结果
     */
    @Override
    public Map<String,Object> asyncData(List<User> list){
        Map<String,Object> result = new HashMap<>();

        //需要同步的数量
        int addcount = list.size();

        //重复数据数量
        int cf = 0;

        try {
            //声明集合用来存需要插入的数据
            List<User> idslist = new ArrayList<User>();
            //声明集合用来存需要插入数据的id，用于判断是否重复
            List<String> l = new ArrayList<>();
            //循环传过来的需要插入的数据列表
            for (int i = 0;i<list.size();i++){
                l.add(list.get(i).getId());
                User user = new User();
                BeanUtils.copyProperties(list.get(i),user);
                idslist.add(user);
            }

            //根据需要插入的数据的id查出重复的数据
            List<User> userList = userService2.getUser(l);
            //如果重复数据list不为空，代表有重复数据
            if (CollectionUtils.isNotEmpty(userList)){
                //去除已经有的数据编号
                for (User ulist: userList) {
                    idslist.removeIf(user -> user.getId().equals(ulist.getId()));
                }
                //操作需要同步的数据数量
                addcount -= userList.size();
                //操作重复数据的数量
                cf += userList.size();
            }

            //如果需要同步数据list不为空，则进行同步数据操作
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
















