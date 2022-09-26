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
public class Material {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String materialType;
    private String structuralElementType;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<MaterialCharacteristics> materialCharacteristics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Material material = (Material) o;
        return id != null && Objects.equals(id, material.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
