package org.services;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import org.dto.GiftDto;
import org.objects.Gift;
import org.objects.Login;
import org.objects.Wishlist;

public class GiftsApi extends AbsApi {

  private String baseGiftsPath = "/api/gifts/";
  private String wishlistPath = "wishlist/";

  public ValidatableResponse addGift(Login login, Wishlist wishlist, GiftDto dto) {
    return given(reqSpec)
        .basePath(baseGiftsPath + wishlistPath + wishlist.getId())
        .auth().oauth2(login.getToken())
        .body(dto)
        .when()
        .post()
        .then()
        .log().all();
  }


  public Gift addGiftWithReturnValue(Login login, Wishlist wishlist, GiftDto dto) {
    return addGift(login, wishlist, dto)
        .extract()
        .as(Gift.class);
  }

  public void deleteGift(Login login, Gift gift) {
    given(reqSpec)
        .basePath(baseGiftsPath + gift.getId())
        .auth().oauth2(login.getToken())
        .when()
        .delete()
        .then()
        .log().all();
  }

  public ValidatableResponse getGiftById(Login login, Gift gift) {
    return given(reqSpec)
        .basePath(baseGiftsPath + gift.getId())
        .auth().oauth2(login.getToken())
        .when()
        .get()
        .then()
        .log().all();
  }
}
