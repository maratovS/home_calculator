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
public class MaterialCharacteristics {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private double width;
    private double thickness;
    private double length;
    private double volume;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PriceList> priceLists;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Results> results;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MaterialCharacteristics that = (MaterialCharacteristics) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
