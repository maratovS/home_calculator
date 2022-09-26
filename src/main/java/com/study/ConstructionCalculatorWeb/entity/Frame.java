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
public class Frame {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int numberOfFloors;
    private int floorNumber;
    private double floorHeight;
    private double outerWallPerimeter;
    private double baseArea;
    private double externalWallThickness;
    private double interiorWallLength;
    private boolean OSB_exteriorWallCladding;
    private boolean vaporBarrierOfExternalWalls;
    private boolean windProtectionOfExternalWalls;
    private boolean insulationLiningOfExternalWalls;
    private double floorThickness;
    private double OSB_forFloors;
    private double vaporBarrierForFloors;
    private double roofWindProtection;
    private double insulationForFloors;
    private double OSB_forInteriorWallCladding;
    @OneToMany(mappedBy = "frame")
    @ToString.Exclude
    private Set<Results> results;
    @OneToMany(mappedBy = "frame")
    @ToString.Exclude
    private Set<aperturesInFrames> apertures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Frame frame = (Frame) o;
        return id != null && Objects.equals(id, frame.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
