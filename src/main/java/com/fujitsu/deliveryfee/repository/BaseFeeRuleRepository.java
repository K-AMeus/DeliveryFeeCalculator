package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
}