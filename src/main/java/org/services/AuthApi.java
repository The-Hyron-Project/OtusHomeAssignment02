package org.services;

import org.DTO.LoginDto;
import org.objects.Login;

import static io.restassured.RestAssured.given;

public class AuthApi extends AbsApi {
  private final String loginPath = "/api/auth/login";

  public Login authorize(LoginDto loginDto){
    return given(reqSpec)
    .basePath(loginPath)
    .body(loginDto)
    .when()
    .post()
    .then()
    .log().all()
    .extract()
    .as(Login.class);
  }
}
