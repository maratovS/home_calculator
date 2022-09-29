package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResultsRepository extends JpaRepository<Results, Long>, JpaSpecificationExecutor<Results> {
}