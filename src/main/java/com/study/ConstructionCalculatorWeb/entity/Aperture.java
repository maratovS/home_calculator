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
public class Aperture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String type;
    private double height;
    private double weight;
    private int quantity;
    @OneToMany(mappedBy = "aperture")
    @ToString.Exclude
    private Set<aperturesInFrames> frames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Aperture aperture = (Aperture) o;
        return id != null && Objects.equals(id, aperture.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
