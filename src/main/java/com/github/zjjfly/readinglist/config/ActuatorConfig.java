package com.github.zjjfly.readinglist.config;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zjjfly
 * @date 2017/7/19
 */
@Configuration
public class ActuatorConfig {
    /*
        自定义的内存Trace仓库，默认的是记录100个请求，这里改成了1000个
     */
    @Bean
    public InMemoryHttpTraceRepository traceRepository() {
        InMemoryHttpTraceRepository traceRepository = new InMemoryHttpTraceRepository();
        traceRepository.setCapacity(1000);
        return traceRepository;
    }
}
