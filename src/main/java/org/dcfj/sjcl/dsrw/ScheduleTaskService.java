package org.dcfj.sjcl.dsrw;

import com.alibaba.fastjson.JSONObject;
import org.dcfj.sjcl.utils.PostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTaskService {
    private Logger logger = LoggerFactory.getLogger(ScheduleTaskService.class);
    public final static String url = "http://localhost:8088/";

    /**
     * 十秒任务进行，定时器的入口，然后通过url访问controller，操作正式开始
     */
    @Scheduled(cron = "0/40 * * * * ?")
    public void task(){
        logger.info("========定时任务开始========");
        JSONObject ob = new JSONObject();
        try {
            PostUtil.doPost(ob.toJSONString(),url+"sync/syncPayHistory","text/plain");
            PostUtil.doPost(ob.toJSONString(),url+"sync/pcsdata","text/plain");
        }catch (Exception e){
            logger.info("========启动失败，原因："+e.getMessage());
        }

        logger.info("=========定时任务结束=========");
    }
}
