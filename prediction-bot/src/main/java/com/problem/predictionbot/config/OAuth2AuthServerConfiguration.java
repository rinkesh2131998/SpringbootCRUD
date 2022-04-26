package com.problem.predictionbot.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * oauth server.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
@ConfigurationProperties(prefix = "user.oauth")
public class OAuth2AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {
  private final String clientId;
  private final String clientSecret;
  private final String redirectUris;
  private final int accessTokenValidity;
  private final int refreshTokenValidity;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitsAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient(clientId)
        .secret(passwordEncoder.encode(clientSecret))
        .authorizedGrantTypes("password", "authorization_code", "refresh_token")
        .scopes("user info")
        .authorities("READ_ONLY_CLIENT")
        .redirectUris(redirectUris)
        .accessTokenValiditySeconds(accessTokenValidity)
        .refreshTokenValiditySeconds(refreshTokenValidity);
  }
}
