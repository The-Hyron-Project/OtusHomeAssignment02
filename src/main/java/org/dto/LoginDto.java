package org.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;
}
