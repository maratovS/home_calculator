package com.study.ConstructionCalculatorWeb.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Frame {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int numberOfFloors;
    private int floorNumber;
    private int floorHeight;
    private double outerWallPerimeter;
    private double baseArea;
    private double externalWallThickness;
    private double internalWallLength;
    private double internalWallThickness;
    private String OSBExternalWall;
    private String steamWaterproofingExternalWall;
    private String windscreenExternalWall;
    private String insulationExternalWall;
    private double overlapThickness;
    private String OSBThickness;
    private String steamWaterproofingThickness;
    private String windscreenProtectionThickness;
    private String insulationThickness;
    private String OSBInternalWall;
    @OneToMany
    @ToString.Exclude
    private List<AperturesInFrames> apertures;

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
