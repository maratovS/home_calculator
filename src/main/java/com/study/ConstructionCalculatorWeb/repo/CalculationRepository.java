package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CalculationRepository extends JpaRepository<Calculation, Long>, JpaSpecificationExecutor<Calculation> {
    Calculation findByNumber(UUID number);
}