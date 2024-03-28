package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing base fee rules in the database.
 * Provides CRUD operations and additional methods for querying base fee rules.
 */
@Repository
public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
}