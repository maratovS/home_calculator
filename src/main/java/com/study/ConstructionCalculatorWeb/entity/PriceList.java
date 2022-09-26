package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PriceList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date dateOfPurchase;
    private double purchasePrice;
    private double sellingPrice;
    @ManyToOne
    @JoinColumn(name = "material_characteristics_id")
    private MaterialCharacteristics materialCharacteristics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PriceList priceList = (PriceList) o;
        return id != null && Objects.equals(id, priceList.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
