package com.github.zjjfly.readinglist.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义健康指示器
 *
 * @author zjjfly
 * @date 2017/7/19
 */
@Component
public class AmazonHealth implements HealthIndicator{
    @Override
    public Health health() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://www.amazon.com",String.class);
            return Health.up().build();
        }catch (Exception e){
            //添加额外的异常信息
            return Health.down().withDetail("reason",e.getMessage()).build();
        }
    }
}
