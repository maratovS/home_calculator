package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Foundation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private double outerWallPerimeter;
    private double interiorWallLength;
    private String concretePiles;
    private String concrete;
    @OneToMany(mappedBy = "foundation")
    @ToString.Exclude
    private Set<Results> results;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Foundation that = (Foundation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
