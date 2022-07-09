package org.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRS {
    private long id;
    private String login;
}