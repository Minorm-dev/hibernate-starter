package com.minorm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SortNatural;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "name")
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    // using mappedBy instead of JoinColumn annotation
//    @JoinColumn(name = "company_id")
//    @OrderBy("personalInfo.firstname")
//    @org.hibernate.annotations.OrderBy(clause = "username DESC, lastname ASC") // deprecated
//    @OrderColumn(name = "id")
    @MapKey(name = "username")
    @SortNatural
    private Map<String, User> users = new TreeMap<>();


    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @AttributeOverride(name = "lang", column = @Column(name = "lang")) // if it has diff names
//    private List<LocaleInfo> locales = new ArrayList<>();
//    @Column(name = "description")
    @MapKeyColumn(name = "lang")
    private Map<String, String > locales = new HashMap<>(); // uses for read-only mode


    public void addUser(User user) {
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }

}
