package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Embeddable
public class aperturesInFramesKey implements Serializable {
    @Column(name = "frame_id")
    private Long frameId;
    @Column(name = "aperture_id")
    private Long apertureId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        aperturesInFrames that = (aperturesInFrames) o;
        return frameId != null && Objects.equals(frameId, that.getFrame().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameId + apertureId);
    }
}
