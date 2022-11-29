package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {
    Status findByStatusName(String name);
}