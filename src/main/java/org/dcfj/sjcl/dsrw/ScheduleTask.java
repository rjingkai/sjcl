package org.dcfj.sjcl.dsrw;

import org.dcfj.sjcl.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component                //声明这是个组件
@EnableScheduling         //开启定时任务
@EnableAsync              //启用多线程
public class ScheduleTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private userService userService;

    @Async
    @Scheduled(fixedDelay = 1000)
    public void first(){
        logger.info("第一个定时任务开始！"+Thread.currentThread().getName() + userService.getUser());
    }

    @Async
    @Scheduled(fixedDelay = 2000)
    public void second(){
        logger.info("第二个定时任务开始！"+Thread.currentThread().getName() + userService.getUser());

    }

}
