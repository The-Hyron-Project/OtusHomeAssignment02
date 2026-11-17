package org.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GiftDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("imageUrl")
  private String imageUrl;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("storeUrl")
  private String storeUrl;

  @JsonProperty("reserved")
  private Boolean reserved;
}
