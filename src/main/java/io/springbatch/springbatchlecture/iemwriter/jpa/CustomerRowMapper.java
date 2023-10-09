package io.springbatch.springbatchlecture.iemwriter.jpa;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer1> {
    @Override
    public Customer1 mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Customer1(resultSet.getLong("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getDate("birthdate"));
    }
}
