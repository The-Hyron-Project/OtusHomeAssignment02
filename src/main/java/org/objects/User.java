package org.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User {
  private String id;
  private String username;
  private String email;

  @JsonCreator
  public User(@JsonProperty("id") String id,
              @JsonProperty("username") String username,
              @JsonProperty("email") String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" + "id='" + id + '\'' + ", username='" + username + '\''
        + ", email='" + email + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(username, user.username)
        && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email);
  }
}
