package org.dcfj.sjcl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.dcfj.sjcl.db2.service.userServiceDb2;
import org.dcfj.sjcl.model.PcsBean;
import org.dcfj.sjcl.service.PcsService;
import org.springframework.beans.BeanUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PcsServiceimpl implements PcsService {

    @Resource(name = "userService2")
    private userServiceDb2 userService2;

    /**
     * 解析json数据，{}：代表着jsonarray; []:代表着jsonobject
     */
    @Override
    public List<PcsBean> parseJson(String str){
        List<PcsBean> list = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        for (int i = 0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            PcsBean bean = new PcsBean();
            bean.setCkzs(object.get("CKZS").toString());
            bean.setLkzs(object.get("LKZS").toString());
            bean.setOrgid(object.get("ORGID").toString());
            bean.setOrgname(object.get("ORGNAME").toString());
            bean.setZkzs(object.get("ZKZS").toString());
            bean.setRownum_(object.get("ROWNUM_").toString());
            list.add(bean);
        }
        return list;
    }

    @Override
    public Map<String,Object> syncData(List<PcsBean> list){
        Map<String,Object> map = new HashMap<>();

        int count = list.size();

        int cf = 0;

        try {
            List<PcsBean> newdatalist = new ArrayList<>();

            List<String> idlist = new ArrayList<>();

            for (int i = 0 ;i<list.size(); i++){
                idlist.add(list.get(i).getOrgid());
                PcsBean pcsBean = new PcsBean();
                BeanUtils.copyProperties(list.get(i),pcsBean);
                newdatalist.add(pcsBean);
            }

            List<PcsBean> plist = userService2.getPcs(idlist);

            if (CollectionUtils.isNotEmpty(plist)){
                for (PcsBean p:plist) {
                    newdatalist.removeIf(pcsBean -> pcsBean.getOrgid().equals(p.getOrgid()));
                }

                count -= plist.size();
                cf += plist.size();
            }

            if (CollectionUtils.isNotEmpty(newdatalist)){
                userService2.batchInsertPcs(newdatalist);
            }
        }catch (Exception e){
            map.put("error",e.getMessage());
        }

        map.put("added", count);
        map.put("repeat", cf);

        return map;
    }
}
