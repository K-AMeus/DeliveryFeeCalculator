package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import com.fujitsu.deliveryfee.service.BaseFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-fee-rules")
public class BaseFeeRuleController {

    private final BaseFeeRuleService baseFeeRuleService;

    @Autowired
    public BaseFeeRuleController(BaseFeeRuleService baseFeeRuleService) {
        this.baseFeeRuleService = baseFeeRuleService;
    }

    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        return ResponseEntity.ok(baseFeeRuleService.createBaseFeeRule(baseFeeRule));
    }

    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        return ResponseEntity.ok(baseFeeRuleService.getAllBaseFeeRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        return baseFeeRuleService.getBaseFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRuleDetails) {
        return ResponseEntity.ok(baseFeeRuleService.updateBaseFeeRule(id, baseFeeRuleDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
