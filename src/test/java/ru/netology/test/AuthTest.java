package ru.netology.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserData;

import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.*;
import static ru.netology.data.UserData.newUser;

public class AuthTest {
    Faker faker = new Faker(new Locale("en"));

    void loginForm(String login, String password) {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }

    @Test
    public void shouldLoginWithValidActiveUser() {
        UserData.UserInfo user = newUser( false);
        loginForm(user.getLogin(), user.getPassword());
        $(byText("Личный кабинет")).waitUntil(visible, 15000);
    }
    @Test
    public void shouldGetErrorIfNotRegisteredUser() {
        UserData.UserInfo user = newUser( false);
        loginForm(faker.name().username(), faker.internet().password());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfUserBlocked() {
        UserData.UserInfo user = newUser( true);
        loginForm(user.getLogin(), user.getPassword());
        $(withText("Пользователь заблокирован")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfInvalidLogin() {
        UserData.UserInfo user = newUser( true);
        loginForm(faker.name().username(), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfInvalidPassword() {
        UserData.UserInfo user = newUser( true);
        loginForm(user.getLogin(), faker.internet().password());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }
}
