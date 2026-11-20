package org.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GiftDto {
  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private Double price;
  private String storeUrl;
  private Boolean reserved;
}
