package org.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginDto {

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;
}
