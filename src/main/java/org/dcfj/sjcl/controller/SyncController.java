package org.dcfj.sjcl.controller;

import org.apache.commons.collections.CollectionUtils;
import org.dcfj.sjcl.dsrw.AsyncService;
import org.dcfj.sjcl.dsrw.AsyncTaskConfig;
import org.dcfj.sjcl.model.User;
import org.dcfj.sjcl.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sync")
public class SyncController {
    private static Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Autowired
    private SyncService syncService;


    @RequestMapping(value = "/syncPayHistory", produces = "text/plain;charset=UTF-8")
    public String syncData() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("【SyncData-" + currentTimeMillis + "】执行开始......");
        //查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        // 状态：待同步
        // 单次同步数量
       // params.put("status", "PENDING");
       // params.put("limitCount", 5000);

        //查出来需要同步的数据
        List<User> list = syncService.getUser();

        if (CollectionUtils.isNotEmpty(list)){
            //注入多线程的bean
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskConfig.class);
            AsyncService service = context.getBean(AsyncService.class);

            //总的同步次数
            int count = list.size();
            // 每次同步500条数据
            int eachTimeSynCount = 500;

            // 执行次数
            int excuteTimes = (count % eachTimeSynCount == 0) ? (count / eachTimeSynCount) : (count / eachTimeSynCount + 1);
            for (int i = 0;i<excuteTimes;i++){
                // 每页第一个数据在result中的位置
                int fromIndex = i * eachTimeSynCount;
                // 每页最后一个数据在result中的位置
                int toIndex = (count - fromIndex) > eachTimeSynCount ? ((i + 1) * eachTimeSynCount) : count;
                String threadName = "Thread-" + currentTimeMillis + "-" + (i + 1);
                try {
                    //开始同步数据
                    service.asyncdatatask(syncService,threadName,list);
                }catch (TaskRejectedException e){
                    logger.info("=============线程池满，等一秒钟=========");
                    Thread.sleep(1000);
                }

            }
            context.close();
        }
        logger.info("【SyncData-" + currentTimeMillis + "】执行结束......");
        return "【SyncData-" + currentTimeMillis + "】执行成功.";
    }

}
