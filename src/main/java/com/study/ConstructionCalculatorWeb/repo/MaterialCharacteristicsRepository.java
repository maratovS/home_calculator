package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.MaterialCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaterialCharacteristicsRepository extends JpaRepository<MaterialCharacteristics, Long>, JpaSpecificationExecutor<MaterialCharacteristics> {
    MaterialCharacteristics findByWidthAndThicknessAndLength(double thickness, double length, double volume);
    MaterialCharacteristics findByName(String name);
}