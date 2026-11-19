package org.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Login {
  private String token;

  @JsonCreator
  public Login(@JsonProperty("token") String accessToken) {
    token = accessToken;
  }
}
