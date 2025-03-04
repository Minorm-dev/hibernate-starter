package com.minorm.entity;

import com.minorm.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "profile", "userChats"})
@Entity
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Comparable<User>, BaseEntity<Long> {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_primaryKey
    private Company company;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

//    public void addChat(Chat chat) {
//        chats.add(chat);
//        chat.getUsers().add(this);
//    }

//    @Column(columnDefinition = "jsonb", name = "info")
//    @JdbcTypeCode(SqlTypes.JSON)
//    private String info;

}
