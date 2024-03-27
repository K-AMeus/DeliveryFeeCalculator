package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import com.fujitsu.deliveryfee.repository.BaseFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseFeeRuleService {

    private final BaseFeeRuleRepository baseFeeRuleRepository;

    @Autowired
    public BaseFeeRuleService(BaseFeeRuleRepository baseFeeRuleRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
    }

    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }

    public Optional<BaseFeeRule> getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id);
    }

    public BaseFeeRule updateBaseFeeRule(Long id, BaseFeeRule baseFeeRuleDetails) {
        BaseFeeRule baseFeeRule = baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaseFeeRule not found for this id :: " + id));
        baseFeeRule.setCity(baseFeeRuleDetails.getCity());
        baseFeeRule.setVehicleType(baseFeeRuleDetails.getVehicleType());
        baseFeeRule.setFee(baseFeeRuleDetails.getFee());
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public void deleteBaseFeeRule(Long id) {
        BaseFeeRule baseFeeRule = baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaseFeeRule not found for this id :: " + id));
        baseFeeRuleRepository.delete(baseFeeRule);
    }
}
