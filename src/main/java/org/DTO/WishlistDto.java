package org.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class WishlistDto {

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;
}
