package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import com.fujitsu.deliveryfee.repository.ExtraFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtraFeeRuleService {

    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    @Autowired
    public ExtraFeeRuleService(ExtraFeeRuleRepository extraFeeRuleRepository) {
        this.extraFeeRuleRepository = extraFeeRuleRepository;
    }

    public ExtraFeeRule createExtraFeeRule(ExtraFeeRule extraFeeRule) {
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public List<ExtraFeeRule> getAllExtraFeeRules() {
        return extraFeeRuleRepository.findAll();
    }

    public Optional<ExtraFeeRule> getExtraFeeRuleById(Long id) {
        return extraFeeRuleRepository.findById(id);
    }

    public ExtraFeeRule updateExtraFeeRule(Long id, ExtraFeeRule extraFeeRuleDetails) {
        ExtraFeeRule extraFeeRule = extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ExtraFeeRule not found for this id :: " + id));
        extraFeeRule.setConditionType(extraFeeRuleDetails.getConditionType());
        extraFeeRule.setConditionValue(extraFeeRuleDetails.getConditionValue());
        extraFeeRule.setFee(extraFeeRuleDetails.getFee());
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public void deleteExtraFeeRule(Long id) {
        ExtraFeeRule extraFeeRule = extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ExtraFeeRule not found for this id :: " + id));
        extraFeeRuleRepository.delete(extraFeeRule);
    }
}
