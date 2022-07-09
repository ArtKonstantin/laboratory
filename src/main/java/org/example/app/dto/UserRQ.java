package org.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRQ {
    private long id;
    private String login;
    private String password;
}