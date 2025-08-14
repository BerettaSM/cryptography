package com.ramon.crypt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "crypt.secret")
@Data
public class CryptSecret {

    private String password;
    private String salt;

}
