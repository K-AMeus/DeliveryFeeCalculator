package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.FeeRuleNotFoundException;
import com.fujitsu.deliveryfee.model.BaseFeeRule;
import com.fujitsu.deliveryfee.repository.BaseFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service class for managing base fee rules.
 * Provides methods to perform CRUD operations on BaseFeeRule entities.
 */
@Service
public class BaseFeeRuleService {

    private final BaseFeeRuleRepository baseFeeRuleRepository;

    @Autowired
    public BaseFeeRuleService(BaseFeeRuleRepository baseFeeRuleRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
    }


    /**
     * Creates a new base fee rule.
     * @param baseFeeRule The base fee rule object to be created
     * @return The created base fee rule
     */
    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    /**
     * Retrieves all base fee rules.
     * @return A list of all base fee rules
     */
    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }


    /**
     * Retrieves a base fee rule by its ID.
     * @param id The ID of the base fee rule to retrieve
     * @return An Optional containing the base fee rule if found, otherwise empty
     */
    public Optional<BaseFeeRule> getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id);
    }


    /**
     * Updates a base fee rule.
     * @param id The ID of the base fee rule to update
     * @param baseFeeRuleDetails The updated details of the base fee rule
     * @return The updated base fee rule
     * @throws FeeRuleNotFoundException if the base fee rule with the given ID is not found
     */
    public BaseFeeRule updateBaseFeeRule(Long id, BaseFeeRule baseFeeRuleDetails) {
        BaseFeeRule baseFeeRule = baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new FeeRuleNotFoundException(id));
        baseFeeRule.setCity(baseFeeRuleDetails.getCity());
        baseFeeRule.setVehicleType(baseFeeRuleDetails.getVehicleType());
        baseFeeRule.setFee(baseFeeRuleDetails.getFee());
        return baseFeeRuleRepository.save(baseFeeRule);
    }


    /**
     * Deletes a base fee rule by its ID.
     * @param id The ID of the base fee rule to delete
     * @throws FeeRuleNotFoundException if the base fee rule with the given ID is not found
     */
    public void deleteBaseFeeRule(Long id) {
        BaseFeeRule baseFeeRule = baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new FeeRuleNotFoundException(id));
        baseFeeRuleRepository.delete(baseFeeRule);
    }
}
