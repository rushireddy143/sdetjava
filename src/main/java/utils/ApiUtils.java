package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {
    public static Response get(String endpoint) {
        return RestAssured.get(endpoint);
    }

    public static Response post(String endpoint, Object body) {
        return RestAssured.given().body(body).post(endpoint);
    }
}