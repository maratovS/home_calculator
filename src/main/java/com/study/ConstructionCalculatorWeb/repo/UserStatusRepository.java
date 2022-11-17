package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.Status;
import com.study.ConstructionCalculatorWeb.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long>, JpaSpecificationExecutor<UserStatus> {
    UserStatus findByStatusName(String statusName);
}