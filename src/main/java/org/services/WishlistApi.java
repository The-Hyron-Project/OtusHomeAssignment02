package org.services;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import org.dto.WishlistDto;
import org.objects.Login;
import org.objects.Wishlist;

public class WishlistApi extends AbsApi {

  private String baseWishlistPath = "/api/wishlists";
  private String forwardSlash = "/";

  public ValidatableResponse createWishlist(Login login, WishlistDto dto) {
    return given(reqSpec).basePath(baseWishlistPath).auth().oauth2(login.getToken()).body(dto)
        .when().post().then().log().all();
  }

  public Wishlist createWishlistWithReturnValue(Login login, WishlistDto dto) {
    return createWishlist(login, dto).extract().as(Wishlist.class);
  }

  public void deleteWishlist(Login login, Wishlist wishlist) {
    given(reqSpec).basePath(baseWishlistPath + forwardSlash + wishlist.getId()).auth()
        .oauth2(login.getToken()).when().delete().then().log().all();
  }

  public Wishlist getWishlistById(Login login, Wishlist wishlist) {
    return given(reqSpec).basePath(baseWishlistPath + forwardSlash + wishlist.getId()).auth()
        .oauth2(login.getToken()).when().get().then().log().all().extract().as(Wishlist.class);
  }

  public Wishlist editWishlist(Login login, Wishlist wishlist, WishlistDto dto) {
    return given(reqSpec).basePath(baseWishlistPath + forwardSlash + wishlist.getId())
        .auth().oauth2(login.getToken()).body(dto).when().put().then().log().all().extract()
        .as(Wishlist.class);
  }
}
