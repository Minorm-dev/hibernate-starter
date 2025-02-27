package com.minorm;

import com.minorm.converter.BirthdayConverter;
import com.minorm.entity.Birthday;
import com.minorm.entity.Role;
import com.minorm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;
//        SessionFactory

//        var connection = DriverManager.
//                getConnection("db.url", "db.username", "db.password");
//        Session

        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
//        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter());
//        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            User user = User.builder()
                    .username("ivan3@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
//                    .info("""
//                            "name": "Ivan",
//                            "id": 25
//                            """)
                    .birthDate(new Birthday(LocalDate.of(2000,1,19)))
                    .role(Role.ADMIN)
                    .build();
            session.delete(user); // need to use persist instead, but for this example save is OK
            session.getTransaction().commit();
        }
    }
}
