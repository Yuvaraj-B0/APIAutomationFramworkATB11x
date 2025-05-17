package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Test(groups = "reg", priority = 0)
    @Owner("Yuvaraj")
    @Description("TC#1  - Create Token and Verify")
    public void testTokenPOST() {
        // Prep of Req
        requestSpecification.basePath(APIConstants.AUTH_URL);
        // Making of the Request.
        response= RestAssured.given(requestSpecification).when().body(payloadManager.setAuthPayload()).post();
        // Extraction ( JSON String response to Java Object
        String token = payloadManager.getTokenFromJSON(response.asString());
        System.out.println(token);

        // Validation of the request.
        assertActions.verifyStringKeyNotNull(token);

        // Add to Allure report
        //Allure.addAttachment("Booking Request payload", "application/json", "{}", ".json");
        Allure.addAttachment("Booking Response", "application/json", response.asString(), ".json");

    }
}
