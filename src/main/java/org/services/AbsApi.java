package org.services;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class AbsApi {
  protected RequestSpecification reqSpec;
  private final String baseUrl = "base.url";
  private final String ContentTypeHeaderName = "Content-Type";
  private final String ContentTypeHeaderContent = "application/json";

  public AbsApi(){
    reqSpec = given()
        .baseUri(System.getProperty(baseUrl))
        .header(ContentTypeHeaderName, ContentTypeHeaderContent)
        .log().all();
  }
}
