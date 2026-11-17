package org.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserRegistrationDto {

  @JsonProperty("username")
  private String username;

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;
}
