package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.AperturesInFrames;
import com.study.ConstructionCalculatorWeb.entity.aperturesInFramesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AperturesInFramesRepository extends JpaRepository<AperturesInFrames, aperturesInFramesKey>, JpaSpecificationExecutor<AperturesInFrames> {
}