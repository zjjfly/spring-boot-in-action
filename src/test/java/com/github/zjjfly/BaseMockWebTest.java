package com.github.zjjfly;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

/**
 * Created by zjjfly on 2017/7/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseMockWebTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mvc;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())//应用spring security模块
                .build();
    }
}
