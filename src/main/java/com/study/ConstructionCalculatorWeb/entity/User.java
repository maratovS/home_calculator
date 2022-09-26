package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private UserStatus status;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "user_groupOfUsers",
            joinColumns = { @JoinColumn(name = "usr_id") },
            inverseJoinColumns = { @JoinColumn(name = "groupOfUsers_id") }
    )
    @ToString.Exclude
    private Set<GroupOfUsers> groupOfUsers;
    private String surname;
    private String name;
    private String patronymic;
    private int telephoneNumber;
    private String email;
    private String login;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
