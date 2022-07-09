package org.example.app.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.app.dto.UserRQ;
import org.example.app.dto.UserRS;
import org.example.app.manager.UserManager;
import org.example.framework.security.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserManager manager;

    @GetMapping("/users")
    public List<UserRS> getAll(@RequestAttribute final Authentication authentication, final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {

        return manager.getAll();
    }

    @GetMapping("/users/{id}")
    public UserRS getById(@RequestAttribute final Authentication authentication, @PathVariable final long id, final HttpServletResponse res) throws ServletException, IOException {

        final UserRS responseDTO = manager.getById(id);
        return responseDTO;
    }

    @PostMapping("/users")
    public void create(@RequestAttribute final Authentication authentication, @RequestBody final UserRQ requestDTO) throws ServletException, IOException {
        manager.create(requestDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@RequestAttribute final Authentication authentication, @PathVariable final long id, final HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        manager.deleteById(id);
    }

    @PutMapping("/users")
    public UserRS update(@RequestAttribute final Authentication authentication, @RequestBody final UserRQ requestDTO) throws ServletException, IOException {
        return manager.update(requestDTO);
    }
}