package com.minorm;

import com.minorm.converter.BirthdayConverter;
import com.minorm.entity.*;
import com.minorm.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class HibernateRunner {

//    Using annotation instead of this
//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {

        Company company = Company.builder()
                .name("Amazon")
                .build();

        User user = User.builder()
                .username("ivan@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Petr")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                        .build())
                .company(company)
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();


                session1.save(user);

                session1.getTransaction().commit();
            }
        }
    }
}
