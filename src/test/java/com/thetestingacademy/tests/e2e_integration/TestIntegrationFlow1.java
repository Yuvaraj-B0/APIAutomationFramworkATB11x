package com.thetestingacademy.tests.e2e_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.reponse.BookingResponse;
import com.thetestingacademy.pojos.request.Booking;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow1 extends BaseTest {

    Integer bookingid;

    @Test(groups = "qa", priority = 1)
    @Owner("Yuvaral")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    public void testCreateBooking(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=  RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString())
                .post();
        validatableResponse = response.then().statusCode(200).log().all();
        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Yuvaraj" );
        bookingid=bookingResponse.getBookingid();

    }
    @Test(groups = "qa", priority = 2)
    @Owner("Yuvaraj")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).when().get();

        validatableResponse = response.then().statusCode(200).log().all();
        // Extraction
        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Yuvaraj" );

    }
    @Test(groups = "qa", priority = 3)
    @Owner("Yuvaraj")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).cookies("token", token).when()
                .body(payloadManager.fullUpdatePayloadAsString()).put();
        validatableResponse = response.then().statusCode(200).log().all();
        // Extraction
        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Ajith" );

    }
    @Test(groups = "qa", priority = 4)
    @Owner("Yuvaraj")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid);
        response=RestAssured.given(requestSpecification).cookies("token", token).when().delete();
        validatableResponse = response.then().statusCode(201).log().all();
        String st=response.asPrettyString();
       // System.out.println(st);
        Assert.assertEquals(st,"Created");

    }


}
