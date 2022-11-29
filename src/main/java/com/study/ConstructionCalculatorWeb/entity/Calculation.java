package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Calculation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String constructionAddress;
    private UUID number;
    private Date dataOfCreation;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @OneToMany
    @ToString.Exclude
    private List<Results> results;

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
