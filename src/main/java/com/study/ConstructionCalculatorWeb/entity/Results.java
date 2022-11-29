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
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String material;
    private double amount;
    private String units;
    private double price;
    private double totalCost;
    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;
    @ManyToOne
    @JoinColumn(name = "frame_id")
    private Frame frame;
    @ManyToOne
    @JoinColumn(name = "material_characteristics_id")
    private MaterialCharacteristics materialCharacteristics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Results results = (Results) o;
        return id != null && Objects.equals(id, results.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
