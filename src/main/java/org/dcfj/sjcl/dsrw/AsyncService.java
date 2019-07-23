package org.dcfj.sjcl.dsrw;

import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AsyncService {

    private static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void asyncdatatask(SyncService SyncService, String threadname, List<User> list){
        Map<String,Object> result =  SyncService.asyncData(list);
        // 执行成功
        if (result.get("error") == null) {
            logger.info("【" + threadname + "】执行成功,新增:" + result.get("added").toString() + "条,重复:"
                    + result.get("repeat").toString() + "条,总共:" + list.size() + "条.");
        } else {
            logger.warn("【" + threadname + "】执行失败,原因:" + result.get("error").toString());
        }
    }
}
