package ru.praktikum.qasqooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CouriersSteps;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.utils.ApiRequestBuilder.courierCreateOrLoginRequest;
//
public class CourierLoginTest extends ConfigTest {
    private final CouriersSteps couriersSteps = new CouriersSteps();
    private Courier courier;

    @Before
    public void init() {
        courier = courierCreateOrLoginRequest();
        couriersSteps.create(courier);
    }

    @Test
    @DisplayName("Incorrect password check")
    public void incorrectPasswordCheck() {
        couriersSteps
                .login(courier.getLogin(), courier.getIncorrectPassword())
                .statusCode(404);
    }

    @Test
    @DisplayName("Incorrect login check")
    public void incorrectLoginCheck() {
        couriersSteps
                .login(courier.getIncorrectLogin(), courier.getPassword())
                .statusCode(404);
    }

    @Test
    @DisplayName("Authorization without login")
    public void authorizationWithoutLogin() {
        couriersSteps
                .login(null, courier.getPassword())
                .statusCode(404);
    }

    @Test
    @DisplayName("Login courier success")
    public void LoginCourierSuccess() {
        couriersSteps
                .create(courier);
        couriersSteps
                .login(courier)
                .statusCode(200);
    }

    @Test
    @DisplayName("Should return id")
    public void shouldReturnId() {
        couriersSteps
                .create(courier);
        couriersSteps
                .login(courier)
                .body("id", notNullValue());
    }

    @After
    public void destroy() {
        couriersSteps.delete(couriersSteps.login(courier)
                .extract().body().path("id"));
    }
}