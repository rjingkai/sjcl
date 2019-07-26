package org.dcfj.sjcl.dsrw;

import org.dcfj.sjcl.model.PcsBean;
import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.service.PcsService;
import org.dcfj.sjcl.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据同步业务类
 */
@Service
public class AsyncService {

    private static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void asyncdatatask(SyncService SyncService, String threadname, List<User> list){
        //调用同步数据方法
        Map<String,Object> result =  SyncService.asyncData(list);
        // 执行成功
        if (result.get("error") == null) {
            logger.info("【" + threadname + "】执行成功,新增:" + result.get("added").toString() + "条,重复:"
                    + result.get("repeat").toString() + "条,总共:" + list.size() + "条.");
        } else {
            logger.warn("【" + threadname + "】执行失败,原因:" + result.get("error").toString());
        }

    }

    @Async
    public void asyncpcsdatatask(PcsService pcsService, String threadname,List<PcsBean> list){
       Map<String,Object> result = pcsService.syncData(list);
        // 执行成功
        if (result.get("error") == null) {
            logger.info("【" + threadname + "】执行成功,新增:" + result.get("added").toString() + "条,重复:"
                    + result.get("repeat").toString() + "条,总共:" + list.size() + "条.");
        } else {
            logger.warn("【" + threadname + "】执行失败,原因:" + result.get("error").toString());
        }
    }
}
