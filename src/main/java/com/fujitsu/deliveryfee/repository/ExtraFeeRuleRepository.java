package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraFeeRuleRepository extends JpaRepository<ExtraFeeRule, Long> {
}
