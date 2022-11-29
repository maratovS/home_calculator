package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@EqualsAndHashCode
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class aperturesInFramesKey implements Serializable {
    @Column(name = "frame_id")
    private Long frameId;
    @Column(name = "aperture_id")
    private Long apertureId;

}
