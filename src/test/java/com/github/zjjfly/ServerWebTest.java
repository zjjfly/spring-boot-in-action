package com.github.zjjfly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Created by zjjfly on 2017/7/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )//使用嵌入的web容器，默认是使用Mock环境
public class ServerWebTest {
    @Value("${local.server.port}")//SpringBoot把随机port放入local.server.port
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test(expected = HttpClientErrorException.class)
    public void pageNotFound(){
        try {
            new RestTemplate().getForObject("http://localhost:"+port+"/bogus",String.class);
        }catch (HttpClientErrorException e){
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            throw e;
        }
    }

}
