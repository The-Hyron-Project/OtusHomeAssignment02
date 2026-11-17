package org.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class WishlistDto {

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;
}
