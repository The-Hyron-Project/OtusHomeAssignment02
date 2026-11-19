package org.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Wishlist {
  private String id;
  private String title;
  private String description;
  private User owner;
  private List<Gift> gifts;

  @JsonCreator
  public Wishlist(@JsonProperty("id") String id,
                  @JsonProperty("title") String title,
                  @JsonProperty("description") String description,
                  @JsonProperty("owner") User owner,
                  @JsonProperty("gifts") List<Gift> gifts) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.owner = owner;
    this.gifts = gifts;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Wishlist wishlist = (Wishlist) o;
    return Objects.equals(id, wishlist.id)
        && Objects.equals(title, wishlist.title)
        && Objects.equals(description, wishlist.description)
        && Objects.equals(owner.getId(), wishlist.owner.getId())
        && Objects.equals(owner.getUsername(), wishlist.owner.getUsername())
        && Objects.equals(owner.getEmail(), wishlist.owner.getEmail())
        && Objects.equals(gifts, wishlist.gifts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, owner, gifts);
  }

  @Override
  public String toString() {
    return "Wishlist{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='"
        + description + '\'' + ", owner=" + owner + ", gifts=" + gifts + '}';
  }
}
