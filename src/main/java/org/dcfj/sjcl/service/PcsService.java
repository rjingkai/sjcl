package org.dcfj.sjcl.service;


import org.dcfj.sjcl.model.PcsBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface PcsService {
    //解析json数据
    List<PcsBean> parseJson(String str);

    Map<String,Object> syncData(List<PcsBean> list);
}
