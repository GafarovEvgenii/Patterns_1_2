package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;
import ru.netology.testmode.data.Registration;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class AuthTest {
    private static Faker faker;

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredActiveUser = DataGenerator.RegistrationUser.getUser("active");
        $("[data-test-id='login'] input").setValue(registeredActiveUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(registeredActiveUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.RegistrationUser.getNotRegisteredUser();
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();
        $(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var registeredActiveUser = DataGenerator.RegistrationUser.getUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredActiveUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(registeredActiveUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();
        $(".notification__content").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.RegistrationUser.getWrongPasswordUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();
        $(".notification__content").shouldHave(text("Неверно указан логин или пароль"));
    }
}
