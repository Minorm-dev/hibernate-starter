package com.minorm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "company")
    // using mappedBy instead of JoinColumn annotation
//    @JoinColumn(name = "company_id")
    private Set<User> users;
}
