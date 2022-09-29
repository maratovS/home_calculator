package com.study.ConstructionCalculatorWeb.repo;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupOfUsersRepository extends JpaRepository<GroupOfUsers, Long> {
    GroupOfUsers findBygroupName(String s);
}