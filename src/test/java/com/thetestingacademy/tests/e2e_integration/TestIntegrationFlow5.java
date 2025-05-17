package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.request.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

public class TestIntegrationFlow5 extends BaseTest {

    //Delete a Booking -> Try to Update it
    Integer bookingid;

    @Test(groups = "qa", priority = 1)
    @Owner("Yuvaral")
    @Description("TC#INT4 - Step 1. Delete the Booking by ID")
    public void testVerifyGetAllBookingId(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response =  RestAssured.given(requestSpecification).when().get();
        validatableResponse = response.then().statusCode(200);
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        bookingid = bookingIds.get(0);

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).cookies("token", token).when().delete();
        validatableResponse = response.then().statusCode(201).log().all();
        String st=response.asPrettyString();
        // System.out.println(st);
        Assert.assertEquals(st,"Created");
    }
    @Test(groups = "qa", priority = 2)
    @Owner("Yuvaraj")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).cookies("token", token).when()
                .body(payloadManager.fullUpdatePayloadAsString()).put();
        validatableResponse = response.then().statusCode(405).log().all();


    }
}
