package org.services;

import org.DTO.WishlistDto;
import org.objects.Wishlist;

import static io.restassured.RestAssured.given;

public class WishlistApi extends AbsApi{

  private String baseWishlistPath = "/api/wishlists";
  private String forwardSlash = "/";

  public Wishlist createWishlist(String token, WishlistDto dto){
    return given(reqSpec)
        .basePath(baseWishlistPath)
        .auth().oauth2(token)
        .body(dto)
        .when()
        .post()
        .then()
        .log().all()
        .extract()
        .as(Wishlist.class);
  }

  public void deleteWishlist(String token, Wishlist wishlist){
    given(reqSpec)
        .basePath(baseWishlistPath + forwardSlash + wishlist.getId())
        .auth().oauth2(token)
        .when()
        .delete()
        .then()
        .log().all();
  }
}
