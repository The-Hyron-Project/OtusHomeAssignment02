package org.services;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import org.objects.Login;
import org.objects.User;
import org.objects.Wishlist;

@Getter
public class UserApi extends AbsApi {
  private final String baseUser = "base.user";
  private final String baseEmail = "base.email";
  private final String secondaryUser = "secondary.user";
  private final String secondaryEmail = "secondary.email";
  private final String allUsersPath = "/api/users";

  public ValidatableResponse getAllUsers(Login login) {
    return given(reqSpec)
        .basePath(allUsersPath)
        .auth().oauth2(login.getToken())
        .when()
        .get()
        .then()
        .log().all();
  }

  public User getCurrentUser(Login login) {
    return Arrays.stream(getAllUsers(login)
        .extract()
        .as(User[].class))
        .filter(u -> Objects.equals(u.getUsername(),
            System.getProperty(baseUser)) && Objects.equals(u.getEmail(),
            System.getProperty(baseEmail)))
        .findFirst()
        .get();
  }

  public User getSecondaryUser(Login login) {
    return Arrays.stream(getAllUsers(login)
            .extract()
            .as(User[].class))
        .filter(u -> Objects.equals(u.getUsername(),
            System.getProperty(secondaryUser)) && Objects.equals(u.getEmail(),
            System.getProperty(secondaryEmail)))
        .findFirst()
        .get();
  }

  public Wishlist[] getUserWishlistsWithReturnValue(Login login, User user) {
    return getUserWishlists(login, user)
        .extract()
        .as(Wishlist[].class);
  }

  public ValidatableResponse getUserWishlists(Login login, User user) {
    return given(reqSpec)
        .basePath(allUsersPath + "/" + user.getId() + "/wishlists")
        .auth().oauth2(login.getToken())
        .when()
        .get()
        .then()
        .log().all();
  }
}
