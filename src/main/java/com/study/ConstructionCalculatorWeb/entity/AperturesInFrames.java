package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AperturesInFrames {
    @EmbeddedId
    private aperturesInFramesKey id;
    @ManyToOne
    @MapsId("apertureId")
    @JoinColumn(name = "aperture_id")
    private Aperture aperture;
    private int amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AperturesInFrames that = (AperturesInFrames) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
