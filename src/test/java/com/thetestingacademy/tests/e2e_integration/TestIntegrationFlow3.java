package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.asserts.AssertActions;
import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

public class TestIntegrationFlow3 extends BaseTest {
    // TestE2EFlow_03

    //  Test E2E Scenario 3  -> Try to Delete that booking

    //  1. Get a Booking from Get All -> bookingID

    // 2. Delete the Booking - Need to get the token, bookingID from above request

    Integer bookingid;

    @Test(groups = "qa", priority = 1)
    @Owner("Yuvaral")
    @Description("TC#INT3 - Step 1. Get a Booking from Get All by bookingID")
    public void testVerifyGetAllBookingId(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response =  RestAssured.given(requestSpecification).when().get();
        validatableResponse = response.then().statusCode(200);
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
          bookingid = bookingIds.get(0);
         //System.out.println(bookingIds);
        assertActions.verifyStringKeyNotNull(response.jsonPath().getList("bookingid").toString());
       }

    @Test(groups = "qa", priority = 2)
    @Owner("Yuvaral")
    @Description("TC#INT3 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).cookies("token", token).when().delete();
        validatableResponse = response.then().statusCode(201).log().all();
        String st=response.asPrettyString();
        // System.out.println(st);
        Assert.assertEquals(st,"Created");
    }


}
