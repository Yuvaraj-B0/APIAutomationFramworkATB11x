package com.thetestingacademy.base;

import com.thetestingacademy.asserts.AssertActions;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.modules.PayloadManager;
import com.thetestingacademy.pojos.request.Booking;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

public class BaseTest {
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;
    public String token;

    @BeforeClass
    public void setup() {
        System.out.println("Setting up BaseTest for class: " + this.getClass().getSimpleName());

        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        // Build base request specification
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build();

        // Generate token once per test class
        requestSpecification.basePath(APIConstants.AUTH_URL);
        response = RestAssured
                .given(requestSpecification)
                .body(payloadManager.setAuthPayload())
                .post();

        token = payloadManager.getTokenFromJSON(response.asString());

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("Token generation failed. Token is null or empty.");
        }

        System.out.println("Token generated successfully for class: " + this.getClass().getSimpleName());
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Tearing down after tests in: " + this.getClass().getSimpleName());
    }
}