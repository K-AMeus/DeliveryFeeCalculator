package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.FeeRuleNotFoundException;
import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import com.fujitsu.deliveryfee.repository.ExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing extra fee rules.
 * Provides methods to perform CRUD operations on ExtraFeeRule entities.
 */
@Service
public class ExtraFeeRuleService {

    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    @Autowired
    public ExtraFeeRuleService(ExtraFeeRuleRepository extraFeeRuleRepository) {
        this.extraFeeRuleRepository = extraFeeRuleRepository;
    }


    /**
     * Creates a new extra fee rule.
     * @param extraFeeRule The extra fee rule to be created
     * @return The created extra fee rule
     */
    public ExtraFeeRule createExtraFeeRule(ExtraFeeRule extraFeeRule) {
        return extraFeeRuleRepository.save(extraFeeRule);
    }


    /**
     * Retrieves all extra fee rules.
     * @return A list of all extra fee rules
     */
    public List<ExtraFeeRule> getAllExtraFeeRules() {
        return extraFeeRuleRepository.findAll();
    }


    /**
     * Retrieves an extra fee rule by its ID.
     * @param id The ID of the extra fee rule to retrieve
     * @return An Optional containing the extra fee rule if found, otherwise empty
     */
    public Optional<ExtraFeeRule> getExtraFeeRuleById(Long id) {
        return extraFeeRuleRepository.findById(id);
    }


    /**
     * Updates an extra fee rule.
     * @param id The ID of the extra fee rule to update
     * @param extraFeeRuleDetails The updated details of the extra fee rule
     * @return The updated extra fee rule
     * @throws FeeRuleNotFoundException if the extra fee rule with the given ID is not found
     */
    public ExtraFeeRule updateExtraFeeRule(Long id, ExtraFeeRule extraFeeRuleDetails) {
        ExtraFeeRule extraFeeRule = extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new FeeRuleNotFoundException(id));
        extraFeeRule.setConditionType(extraFeeRuleDetails.getConditionType());
        extraFeeRule.setConditionValue(extraFeeRuleDetails.getConditionValue());
        extraFeeRule.setFee(extraFeeRuleDetails.getFee());
        return extraFeeRuleRepository.save(extraFeeRule);
    }


    /**
     * Deletes an extra fee rule by its ID.
     * @param id The ID of the extra fee rule to delete
     * @throws FeeRuleNotFoundException if the extra fee rule with the given ID is not found
     */
    public void deleteExtraFeeRule(Long id) {
        ExtraFeeRule extraFeeRule = extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new FeeRuleNotFoundException(id));
        extraFeeRuleRepository.delete(extraFeeRule);
    }
}
