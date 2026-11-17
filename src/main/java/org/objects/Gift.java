package org.objects;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Gift {

  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private String storeUrl;
  private Double price;
  private Boolean reserved;
  private Wishlist wishList;
}
