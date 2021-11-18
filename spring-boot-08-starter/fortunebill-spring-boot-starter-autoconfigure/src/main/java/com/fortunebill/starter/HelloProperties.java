package com.fortunebill.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kavin
 * @date 2021年11月18日15:45:44
 */
@ConfigurationProperties(prefix = "fortunebill.hello")
public class HelloProperties {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
