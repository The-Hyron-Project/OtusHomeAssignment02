package org.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Login {
  private String token;

  @JsonCreator
  public Login(@JsonProperty("token") String accessToken) {
    token = accessToken;
  }
}
