package org.example.app.manager;

import org.example.app.dto.UserDTO;
import org.example.app.exception.UserNotFoundExeption;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserManager {
    private long nextId = 1;
    private final List<UserDTO> users = new ArrayList<>();

    public List<UserDTO> getAll() {
        return users;
    }


    public UserDTO create(String login) {
        UserDTO user = new UserDTO(nextId++, login);
        users.add(user);
        return user;
    }

    public UserDTO getById(int id) {
        for (UserDTO user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundExeption();
    }

    public void deleteById(int id) {
        for (UserDTO user : users) {
            if (user.getId() == id) {
                users.remove(id);
            }
        }
        throw new UserNotFoundExeption();
    }
}
