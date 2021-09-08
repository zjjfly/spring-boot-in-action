package com.github.zjjfly.readinglist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zjjfly on 2017/7/5.
 */
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associatedId;

    public String getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(String associatedId) {
        this.associatedId = associatedId;
    }
}
