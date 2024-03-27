package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import com.fujitsu.deliveryfee.service.ExtraFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extra-fee-rules")
public class ExtraFeeRuleController {

    private final ExtraFeeRuleService extraFeeRuleService;

    @Autowired
    public ExtraFeeRuleController(ExtraFeeRuleService extraFeeRuleService) {
        this.extraFeeRuleService = extraFeeRuleService;
    }

    @PostMapping
    public ResponseEntity<ExtraFeeRule> createExtraFeeRule(@RequestBody ExtraFeeRule extraFeeRule) {
        return ResponseEntity.ok(extraFeeRuleService.createExtraFeeRule(extraFeeRule));
    }

    @GetMapping
    public ResponseEntity<List<ExtraFeeRule>> getAllExtraFeeRules() {
        return ResponseEntity.ok(extraFeeRuleService.getAllExtraFeeRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> getExtraFeeRuleById(@PathVariable Long id) {
        return extraFeeRuleService.getExtraFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> updateExtraFeeRule(@PathVariable Long id, @RequestBody ExtraFeeRule extraFeeRuleDetails) {
        return ResponseEntity.ok(extraFeeRuleService.updateExtraFeeRule(id, extraFeeRuleDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtraFeeRule(@PathVariable Long id) {
        extraFeeRuleService.deleteExtraFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
