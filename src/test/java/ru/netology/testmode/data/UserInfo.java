package ru.netology.testmode.data;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserInfo {
    private String login;
    private String password;
    private String status;
}
