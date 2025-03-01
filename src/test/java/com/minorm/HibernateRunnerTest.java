package com.minorm;

import com.minorm.entity.Company;
import com.minorm.entity.User;
import com.minorm.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;
import static java.util.stream.Collectors.*;

class HibernateRunnerTest {

    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println();

        session.getTransaction().commit();
    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;
        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        var preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field field : declaredFields) {
            field.setAccessible(true);
            preparedStatement.setObject(1, field.get(user));
        }
    }


}