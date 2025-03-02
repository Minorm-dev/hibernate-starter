package com.minorm;

import com.minorm.entity.*;
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
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;
import static java.util.stream.Collectors.*;

class HibernateRunnerTest {

    @Test
    void checkManyToMany(){
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()){
            session.beginTransaction();

            var user = session.get(User.class, 5L);
            var chat = session.get(Chat.class, 1L);

            var userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();
            userChat.setUser(user);
            userChat.setChat(chat);

            session.save(userChat);

//            user.getChats().clear();

//            var chat = Chat.builder()
//                    .name("minorm")
//                    .build();
//
//            user.addChat(chat);
//            session.save(chat);


            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()){
            session.beginTransaction();

            var user = session.get(User.class, 6L);
            System.out.println();


//            var user = User.builder()
//                    .username("test4@gmail.com")
//                    .build();
//
//            var profile = Profile.builder()
//                    .language("ru")
//                    .street("Lenina 20")
//                    .build();
//
//            profile.setUser(user);
//            session.save(user);
//            profile.setUser(user);
//            session.save(profile);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrphanRemoval() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()){
            session.beginTransaction();

            Company company = session.get(Company.class, 1);
            company.getUsers().removeIf(user -> user.getId().equals(1L));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()){
            session.beginTransaction();

            company = session.get(Company.class, 3);


            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size()); //throw exception because of session

    }

    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 3);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook")
                .build();

        var user = User.builder()
                .username("sveta@gmail.com")
                .build();

//        user.setCompany(company);
//        company.getUsers().add(user);
        company.addUser(user);

        session.save(company); // CascadeType.ALL

        session.getTransaction().commit();
    }

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