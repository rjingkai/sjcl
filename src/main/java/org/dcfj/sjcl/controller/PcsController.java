package org.dcfj.sjcl.controller;

import org.dcfj.sjcl.dsrw.AsyncService;
import org.dcfj.sjcl.dsrw.AsyncTaskConfig;
import org.dcfj.sjcl.model.PcsBean;
import org.dcfj.sjcl.service.PcsService;
import org.dcfj.sjcl.utils.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/sync")
@RestController
public class PcsController {


    private static Logger logger = LoggerFactory.getLogger(PcsController.class);

    @Autowired
    private PcsService pcsService;

    @Value("${jurl}")
    private String url;


    @RequestMapping(value = "/pcsdata",produces = "text/plain;charset=UTF-8")
    public String pcsdata(){

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("【pcssyncdata-" + currentTimeMillis + "】执行开始......");


        HttpClient httpClient = new HttpClient();
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        String result = httpClient.client(url,method,params);
        List<PcsBean> list = pcsService.parseJson(result);
        if (result.length()>0){
            //注入多线程的bean
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskConfig.class);
            AsyncService service = context.getBean(AsyncService.class);
            String threadname = "pcs";
            try {
                //开始同步数据
                service.asyncpcsdatatask(pcsService,threadname+1,list);
            }catch (TaskRejectedException e){
                logger.info("=============线程池满，等一秒钟=========");

            }
            context.close();
        }
        logger.info("【pcssyncdata-" + currentTimeMillis + "】执行结束......");
        return "【pcssyncdata-" + currentTimeMillis + "】执行成功.";
    }


}
