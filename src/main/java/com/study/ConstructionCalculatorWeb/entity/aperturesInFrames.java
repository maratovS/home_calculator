package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class aperturesInFrames {
    @EmbeddedId
    private aperturesInFramesKey id;
    @ManyToOne
    @MapsId("frameId")
    @JoinColumn(name = "frame_id")
    private Frame frame;
    @ManyToOne
    @MapsId("apertureId")
    @JoinColumn(name = "aperture_id")
    private Aperture aperture;
    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        aperturesInFrames that = (aperturesInFrames) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
