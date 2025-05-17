package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.reponse.BookingResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.InputStream;


public class TestCreateBooking extends BaseTest {
    @Test(groups = "reg", priority = 1)
    @Owner("Yuvaraj")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString())
                .log().all().post();
       // System.out.println(response.asString());
        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
       assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
       assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Yuvaraj");

        // Add to Allure report
        Allure.addAttachment("Booking Request payload", "application/json", payloadManager.createPayloadBookingAsString(), ".json");
        Allure.addAttachment("Booking Response", "application/json", response.asString(), ".json");

    }
    @Test(groups = "reg", priority = 2)
    @Owner("Yuvaraj")
    @Description("TC#2 - Verify that the Booking can't be Created, When Payload is null")
    public void testCreateBookingPOST_Negative() {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response=RestAssured.given(requestSpecification).when().body("{}")
                .log().all().post();
       // System.out.println(response.asString());
        // Extraction
        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(500);

      // Add to Allure report
        Allure.addAttachment("Booking Request payload", "application/json", "{}", ".json");
        Allure.addAttachment("Booking Response", "application/json", response.asString(), ".json");


    }
    @Test(groups = "reg", priority = 3)
    @Owner("Yuvaraj")
    @Description("TC#3 - Verify that the Booking can be Created, When Payload is CHINESE")
    public void testCreateBookingPOST_POSITIVE_CHINESE() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsStringWrongBody()).log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);
        // Add to Allure report
        Allure.addAttachment("Booking Request payload", "application/json", payloadManager.createPayloadBookingAsStringWrongBody(), ".json");
        Allure.addAttachment("Booking Response", "application/json", response.asString(), ".json");

    }

}
