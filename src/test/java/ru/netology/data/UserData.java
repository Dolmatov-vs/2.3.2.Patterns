package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class UserData {
    private UserData() {
    }

    @Value
    @AllArgsConstructor
    public static class UserInfo {
        private String login;
        private String password;
        private String status;
    }

    public static UserInfo getUserInfo(boolean isBlocked) {
        Faker faker = new Faker(new Locale("en"));
        return new UserInfo(
                faker.name().username(),
                faker.internet().password(5, 5),
                (isBlocked) ? "blocked" : "active");
    }

}

