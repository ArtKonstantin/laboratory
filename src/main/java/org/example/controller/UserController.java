package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDTO;
import org.example.manager.UserManager;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserManager manager;
    Gson gson = new Gson();
    String login = "petya";
    public void getAll(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        List<UserDTO> responceDTO = manager.getAll();
        res.getWriter().write(gson.toJson(responceDTO));
    }

    public void getById(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        UserDTO responceDTO = manager.getById(Integer.parseInt(req.getParameter("id")));
        res.getWriter().write(gson.toJson(responceDTO));
    }

    public void create(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        UserDTO responceDTO = manager.create(req.getParameter("name"));
        res.getWriter().write(gson.toJson(responceDTO));
    }

    public void deleteById(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        manager.deleteById(Integer.parseInt(req.getParameter("id")));
    }
}
