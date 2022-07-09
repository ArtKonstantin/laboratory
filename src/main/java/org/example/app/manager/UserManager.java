package org.example.app.manager;

import lombok.RequiredArgsConstructor;
import org.example.app.dto.UserRQ;
import org.example.app.dto.UserRS;
import org.example.app.exception.UserLoginAlreadyRegisteredException;
import org.example.app.exception.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final NamedParameterJdbcOperations jdbcOperations;
    private final RowMapper<UserRS> rowMapper = (rs, rowNum) -> new UserRS(rs.getLong("id"), rs.getString("login"));

    public List<UserRS> getAll() {
        return jdbcOperations.query(
                // language=PostgreSQL
                """
                        SELECT id, login FROM users
                        """, rowMapper);
    }

    public UserRS getById(final long id) {
        try {
            return jdbcOperations.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, login FROM users WHERE id = :id
                            """, Map.of("id", id), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    public UserRS create(final UserRQ requestDTO) {

        final boolean loginAlreadyRegistered = jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        SELECT count(*) != 0 FROM users WHERE login = :login
                        """, Map.of("login", requestDTO.getLogin()), Boolean.class);
        if (loginAlreadyRegistered) {
            throw new UserLoginAlreadyRegisteredException(requestDTO.getLogin());
        }

        return jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO users(login, password) VALUES (:login, :password) RETURNING id, login
                        """,
                Map.of("login", requestDTO.getLogin(), "password", requestDTO.getPassword()),
                rowMapper
        );
    }

    public void deleteById(final long id) {

//            final boolean idNotFound = jdbcOperations.queryForObject(
//                    // language=PostgreSQL
//                    """
//                            SELECT count(*) != 0 FROM users WHERE id = :id
//                            """, Map.of("login", id), Boolean.class);
//            if (!idNotFound) {
//                throw new UserNotFoundException();
//            }

            jdbcOperations.update(
                    // language=PostgreSQL
                    """
                            DELETE FROM users WHERE id = :id
                            """,
                    Map.of("id", id)
            );
    }

    public UserRS update(UserRQ requestDTO) {
        return jdbcOperations.queryForObject(
                // language=PostgreSQL
                """
                        UPDATE users
                        SET login = :login, password = :password WHERE id = :id
                        RETURNING id, login
                        """,
                Map.of("login", requestDTO.getLogin(), "password", requestDTO.getPassword(), "id", requestDTO.getId()),
                rowMapper
        );
    }
}