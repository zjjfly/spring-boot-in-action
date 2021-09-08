package com.github.zjjfly.readinglist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 几种异步请求的实现
 *
 * @author zjjfly[https://github.com/zjjfly] on 2021/1/29
 */
@RestController
@Slf4j
public class AsyncController {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 使用servlet自带的异步机制
     */
    @GetMapping("/servletAsync")
    public void servletAsync(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) {
                log.info("超时了...");
                //做一些超时后的相关操作...
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
                log.info("线程开始");
            }

            @Override
            public void onError(AsyncEvent event) {
                log.info("发生错误：" + event.getThrowable());
            }

            @Override
            public void onComplete(AsyncEvent event) {
                log.info("执行完成");
                //这里可以做一些清理资源的操作...
            }
        });
        asyncContext.setTimeout(5000L);
        asyncContext.start(() -> {
            try {
                Thread.sleep(3000L);
                log.info("内部线程：" + Thread.currentThread().getName());
                asyncContext.getResponse().setCharacterEncoding("utf-8");
                asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                asyncContext.getResponse().getWriter().println("这是异步的请求返回");
            } catch (IOException | InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            asyncContext.complete();
        });
        log.info("主线程：" + Thread.currentThread().getName());
    }

    /**
     * 使用Callable和interceptor
     */
    @GetMapping(value = "/callableAsync")
    public Callable<String> callableReq() {
        log.info("外部线程：" + Thread.currentThread().getName());
        return () -> {
            Thread.sleep(4000L);
            log.info("内部线程：" + Thread.currentThread().getName());
            return "Success!";
        };
    }

    /**
     * 使用WebAsyncTask,可以处理一些相对复杂一些的业务逻辑
     * 最主要还是可以在另一个线程里面进行业务处理及返回，即可在两个完全不相干的线程间的通信
     */
    @GetMapping(value = "/webAsyncTask")
    public WebAsyncTask<String> webAsyncTask() {
        log.info("外部线程：" + Thread.currentThread().getName());
        Callable<String> callable = () -> {
            Thread.sleep(4000L);
            log.info("内部线程：" + Thread.currentThread().getName());
            return "Success!";
        };
        WebAsyncTask<String> asyncTask = new WebAsyncTask<>(3000L, callable);
        asyncTask.onTimeout(() -> "超时");
        return asyncTask;
    }

    /**
     * 使用DeferredResult
     */
    @GetMapping(value = "/deferredResult")
    public DeferredResult<String> deferredResult() {
        log.info("外部线程：" + Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<>(60 * 1000L);
        result.onTimeout(() -> {
            result.setResult("超时");
        });
        result.onCompletion(() -> {
            log.info("完成");
        });
        threadPoolTaskExecutor.execute(() -> {
            try {
                Thread.sleep(3000L);
                log.info("内部线程：" + Thread.currentThread().getName());
                //返回结果
                result.setResult("DeferredResult!!");
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        });
        return result;
    }

}

