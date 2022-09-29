package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.aperturesInFrames;
import com.study.ConstructionCalculatorWeb.entity.aperturesInFramesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface aperturesInFramesRepository extends JpaRepository<aperturesInFrames, aperturesInFramesKey>, JpaSpecificationExecutor<aperturesInFrames> {
}