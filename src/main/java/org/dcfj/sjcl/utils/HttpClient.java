package org.dcfj.sjcl.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class HttpClient {

    public  String client(String url, HttpMethod method, MultiValueMap<String,String> params){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response1 = restTemplate.getForEntity(url,String.class);
        return response1.getBody();
    }

}
