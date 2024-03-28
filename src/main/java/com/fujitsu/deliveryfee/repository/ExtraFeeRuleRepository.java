package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for accessing and managing extra fee rules in the database.
 * Provides CRUD operations and additional methods for querying extra fee rules.
 */
@Repository
public interface ExtraFeeRuleRepository extends JpaRepository<ExtraFeeRule, Long> {
}
