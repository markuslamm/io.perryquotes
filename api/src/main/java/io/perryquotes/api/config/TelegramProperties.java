package io.perryquotes.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@PropertySource("classpath:telegram.properties")
public class TelegramProperties {

  @NotBlank
  private String apiKey;

  @NotBlank
  private String responseChannel;

  @NotBlank
  private String responseUrl;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getResponseChannel() {
    return responseChannel;
  }

  public void setResponseChannel(String responseChannel) {
    this.responseChannel = responseChannel;
  }

  public String getResponseUrl() {
    return responseUrl;
  }

  public void setResponseUrl(String responseUrl) {
    this.responseUrl = responseUrl;
  }
}
