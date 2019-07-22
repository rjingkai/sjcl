package org.dcfj.sjcl.dsrw;

import org.dcfj.sjcl.config.HttpClient;
import org.dcfj.sjcl.db1.service.userServiceDb1;
import org.dcfj.sjcl.db2.service.userServiceDb2;
import org.dcfj.sjcl.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component                //声明这是个组件
@EnableScheduling         //开启定时任务
@EnableAsync              //启用多线程
public class ScheduleTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Value("${url}")
    String url;

    @Autowired
    private HttpClient httpClient;

    @Resource(name = "userService1")
    private userServiceDb1 userService1;

    @Resource(name = "userService2")
    private userServiceDb2 userService2;


    @Async
    @Scheduled(fixedDelay = 1000)
    public void first(){
        logger.info("第一个定时任务开始！"+Thread.currentThread().getName() + userService1.getUser());
        List<User> list = userService1.getUser();
        for (int i =0;i<list.size();i++){
            System.out.println(list.get(i).getUsername());
        }
    }

    @Async
    @Scheduled(fixedDelay = 2000)
    public void second(){
        logger.info("第二个定时任务开始！"+Thread.currentThread().getName() + userService2.getUser());
        List<User> list = userService2.getUser();
        for (int i =0;i<list.size();i++){
            System.out.println(list.get(i).getUsername());
        }
    }

//    @Async
//    @Scheduled(fixedDelay = 2000)
//    public void three(){
//        logger.info("第三个定时任务开始！");
//        System.out.println(url);
//        HttpMethod method = HttpMethod.GET;
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        String result = httpClient.client(url,method,params);
//        System.out.println(result);
//    }

}
