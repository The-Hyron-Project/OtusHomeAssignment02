package org.services;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;

public abstract class AbsApi {
  protected RequestSpecification reqSpec;
  private final String baseUrl = "base.url";
  private final String contentTypeHeaderName = "Content-Type";
  private final String contentTypeHeaderContent = "application/json";

  public AbsApi() {
    reqSpec = given()
        .baseUri(System.getProperty(baseUrl))
        .header(contentTypeHeaderName, contentTypeHeaderContent)
        .log().all();
  }
}
