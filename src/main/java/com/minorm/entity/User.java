package com.minorm.entity;

import com.minorm.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    private String username;
    private String firstname;
    private String lastname;

//    @Convert(converter = BirthdayConverter.class)
    @Column(name= "birth_date")
    private Birthday birthDate;

//    @Column(columnDefinition = "jsonb", name = "info")
//    @JdbcTypeCode(SqlTypes.JSON)
//    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;
}
