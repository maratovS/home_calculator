package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Calculation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String constructionAddress;
    private int number;
    private Date dataOfCreation;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @OneToMany(mappedBy = "calculation")
    @ToString.Exclude
    private Set<Results> results;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Calculation that = (Calculation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
