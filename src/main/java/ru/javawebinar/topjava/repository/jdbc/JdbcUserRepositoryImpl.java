package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER_USER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchUpdate("INSERT INTO user_roles (role, user_id) VALUES (?, ?)", user);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay " +
                            "WHERE id=:id", parameterSource);

            batchUpdate("UPDATE user_roles SET role=? WHERE user_id=?", user);
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER_USER, id);
        User user = DataAccessUtils.singleResult(users);
        SqlRowSet rolesRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles " +
                "WHERE user_id=?", id);
        while (rolesRowSet.next()) {
            user.getRoles().add(Role.valueOf(rolesRowSet.getString("role")));
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER_USER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER_USER, email);
        User user = DataAccessUtils.singleResult(users);
        SqlRowSet rolesRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles " +
                "WHERE user_id=?", user.getId());
        while (rolesRowSet.next()) {
            user.getRoles().add(Role.valueOf(rolesRowSet.getString("role")));
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER_USER);
        SqlRowSet rolesRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles ");
        while (rolesRowSet.next()) {
            //user.getRoles().add(Role.valueOf(rolesRowSet.getString("role")));
            for (User user : users) {
                if (user.getId().equals(rolesRowSet.getInt("user_id"))) {
                    user.getRoles().add(Role.valueOf(rolesRowSet.getString("role")));
                }
            }
        }

        return users;
    }

    private void batchUpdate(String sql, User user) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Role role = new ArrayList<>(user.getRoles()).get(i);
                preparedStatement.setInt(2, user.getId());
                preparedStatement.setString(1, role.toString());
            }

            @Override
            public int getBatchSize() {
                return user.getRoles().size();
            }
        });
    }
}
