package com.problem.predictionbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * properties for oauth from yml.
 */
@Data
@Component
@ConfigurationProperties(prefix = "user.oauth")
public class OAuth2Properties {
  private String clientId;
  private String clientSecret;
  private String redirectUris;
  private String username;
  private String password;
  private int accessTokenValidity;
  private int refreshTokenValidity;

  public OAuth2Properties() {
  }
}
