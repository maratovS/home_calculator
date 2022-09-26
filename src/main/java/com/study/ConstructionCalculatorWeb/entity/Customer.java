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
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private int telephoneNumber;
    private String email;
    private String address;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<User> users;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Calculation> calculations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
