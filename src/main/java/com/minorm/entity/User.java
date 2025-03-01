package com.minorm.entity;

import com.minorm.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company")
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
//
//    @Id
//    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @EmbeddedId
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id") // company_primaryKey
    private Company company;

//    @Column(columnDefinition = "jsonb", name = "info")
//    @JdbcTypeCode(SqlTypes.JSON)
//    private String info;

}
