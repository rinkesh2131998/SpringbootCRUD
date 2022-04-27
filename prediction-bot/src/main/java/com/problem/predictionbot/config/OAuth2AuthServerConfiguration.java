package com.problem.predictionbot.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
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
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuth2AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private final OAuth2Properties properties;

  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitsAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient(properties.getClientId())
        .authorizedGrantTypes("password", "authorization_code", "refresh_token")
        .secret(passwordEncoder.encode(properties.getClientSecret()))
        .scopes("user info")
        .authorities("READ_ONLY_CLIENT")
        .redirectUris(properties.getRedirectUris())
        .accessTokenValiditySeconds(properties.getAccessTokenValidity())
        .refreshTokenValiditySeconds(properties.getRefreshTokenValidity());
  }
}
