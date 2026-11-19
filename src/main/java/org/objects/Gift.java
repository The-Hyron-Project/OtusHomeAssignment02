package org.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Gift {

  private String id;
  private String name;
  private String description;
  private String imageUrl;
  private String storeUrl;
  private Double price;
  private Boolean reserved;
  private Wishlist wishList;

  @JsonCreator
  public Gift(@JsonProperty("id") String id,
              @JsonProperty("name") String name,
              @JsonProperty("description") String description,
              @JsonProperty("imageUrl") String imageUrl,
              @JsonProperty("storeUrl") String storeUrl,
              @JsonProperty("price") Double price,
              @JsonProperty("reserved") Boolean reserved,
              @JsonProperty("wishList") Wishlist wishList) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.storeUrl = storeUrl;
    this.price = price;
    this.reserved = reserved;
    this.wishList = wishList;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Gift gift = (Gift) o;
    return Objects.equals(id, gift.id)
        && Objects.equals(name, gift.name)
        && Objects.equals(description, gift.description)
        && Objects.equals(imageUrl, gift.imageUrl)
        && Objects.equals(storeUrl, gift.storeUrl)
        && Objects.equals(price, gift.price)
        && Objects.equals(reserved, gift.reserved);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, imageUrl, storeUrl, price, reserved, wishList);
  }

  @Override
  public String toString() {
    return "Gift{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='"
        + description + '\'' + ", imageUrl='" + imageUrl + '\'' + ", storeUrl='" + storeUrl
        + '\'' + ", price=" + price + ", reserved=" + reserved + '}';
  }
}
