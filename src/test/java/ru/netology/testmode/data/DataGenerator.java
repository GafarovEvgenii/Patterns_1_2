package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void makeRequest(Registration registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private DataGenerator() {
    }

    public static class RegistrationUser {
        private RegistrationUser() {
        }

        public static String getRandomLogin() {
            return faker.name().username();
        }

        public static String getRandomPassword() {
            return faker.internet().password(8, 20);
        }

        public static Registration getUser(String status) {
            var registration = new Registration(getRandomLogin(), getRandomPassword(), status);
            makeRequest(registration);
            return registration;
        }

        public static Registration getNotRegisteredUser() {
            var registration = new Registration(getRandomLogin(), getRandomPassword());
            return registration;
        }

        public static Registration getWrongPasswordUser(String status) {
            String login = getRandomLogin();
            makeRequest(new Registration(login, getRandomPassword(), status));
            return new Registration(login, status);
        }

        public static Registration getWrongLoginUser(String status) {
            String password = getRandomPassword();
            makeRequest(new Registration(getRandomLogin(), password, status));
            return new Registration(getRandomLogin(), password, status);
        }

    }
}
