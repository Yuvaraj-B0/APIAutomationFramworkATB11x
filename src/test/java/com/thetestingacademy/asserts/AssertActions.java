package com.thetestingacademy.asserts;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;

public class AssertActions {

    @Step("Verify response body equals '{expected}' with description: {description}")
    public void verifyResponseBody(String actual, String expected, String description){
        assertEquals(actual, expected, description);
    }

    @Step("Verify response body equals '{expected}' with description: {description}")
    public void verifyResponseBody(int actual, int expected, String description) {
        assertEquals(actual, expected, description);
    }

    @Step("Verify status code is {expected}")
    public void verifyStatusCode(Response response, Integer expected) {
        assertEquals(response.getStatusCode(), expected);
    }

    @Step("Verify key '{keyActual}' equals expected '{keyExpect}' and is not null or blank")
    public void verifyStringKey(String keyExpect, String keyActual){
        assertThat(keyExpect).isNotNull();
        assertThat(keyExpect).isNotBlank();
        assertThat(keyExpect).isEqualTo(keyActual);
    }

    @Step("Verify Integer key is not null")
    public void verifyStringKeyNotNull(Integer keyExpect){
        assertThat(keyExpect).isNotNull();
    }

    @Step("Verify String key is not null")
    public void verifyStringKeyNotNull(String keyExpect){
        assertThat(keyExpect)
                .as("Expected token string to be non-null and non-empty")
                .isNotNull()
                .isNotEmpty()
                .doesNotContainOnlyWhitespaces();
    }
}
