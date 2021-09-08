package com.github.zjjfly;

import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * 使用selenium进行浏览器测试
 * Created by zjjfly on 2017/7/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )//使用嵌入的web容器，默认是使用Mock环境
public class BrowserTest {
    @Value("${local.server.port}")//SpringBoot把随机port放入local.server.port
    private int port;
    private static ChromeDriver browser;
    @BeforeClass
    public static void openBrowser(){
        //使用ChromeDriver需要把可执行的chromedriver放入系统path或者在运行的时候加上参数-Dwebdriver.chrome.driver=chromedriver的路径
        browser=new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterClass
    public static void closeBrowser(){
        browser.quit();
    }

    @Test
    public void addBook2EmptyList(){
        String url = "http://localhost:" + port+"/readinglist/jjzi";
        browser.get(url);
        assertEquals("Login With Username and Password",browser.findElementByClassName("info").getText());
    }
}
