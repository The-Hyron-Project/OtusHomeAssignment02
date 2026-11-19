package org.services;

import static io.restassured.RestAssured.given;

import org.dto.LoginDto;
import org.objects.Login;

public class AuthApi extends AbsApi {
  private final String loginPath = "/api/auth/login";

  public Login authorize(LoginDto loginDto) {
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
