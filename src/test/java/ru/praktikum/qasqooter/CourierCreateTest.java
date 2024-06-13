package ru.praktikum.qasqooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CouriersSteps;

import static org.hamcrest.CoreMatchers.is;
import static ru.praktikum.utils.ApiRequestBuilder.*;
//
public class CourierCreateTest extends ConfigTest {
    private final CouriersSteps couriersSteps = new CouriersSteps();
    private Courier courier;

    @Before
    public void init() {
        courier = courierCreateOrLoginRequest();
    }

    @Test
    @DisplayName("Status code 200")
    public void statusCode() {
        couriersSteps
                .create(courier)
                .statusCode(201);
    }

    @Test
    @DisplayName("Request returns Ok: true")
    public void requestReturnsOkTrue() {
        couriersSteps
                .create(courier)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Create two identical couriers")
    public void createTwoIdenticalCouriers() {
        courier = courierCreateOrLoginRequest();
        couriersSteps
                .create(courier);
        couriersSteps
                .create(courier)
                .statusCode(409);
    }

    @Test
    @DisplayName("Create courier without login")
    public void createCourierWithoutLogin() {
        courier = courierCreateRequestWithoutLogin();
        couriersSteps
                .create(courier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Create courier without password")
    public void createCourierWithoutPassword() {
        courier = courierCreateRequestWithoutPassword();
        couriersSteps
                .create(courier)
                .statusCode(400);
    }

    @After
    public void destroy() {
        couriersSteps.delete(couriersSteps.login(courier)
                .extract().body().path("id"));
    }
}
