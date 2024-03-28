package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import com.fujitsu.deliveryfee.service.BaseFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * Rest controller for managing base fee rules.
 * Provides endpoints for CRUD operations on base fee rules, which define the foundational
 * fees applied for deliveries based on the city and vehicle type.
 */
@RestController
@RequestMapping("/api/base-fee-rules")
public class BaseFeeRuleController {

    private final BaseFeeRuleService baseFeeRuleService;

    @Autowired
    public BaseFeeRuleController(BaseFeeRuleService baseFeeRuleService) {
        this.baseFeeRuleService = baseFeeRuleService;
    }


    /**
     * Creates a new base fee rule.
     *
     * @param baseFeeRule The base fee rule to create.
     * @return The created base fee rule wrapped in a ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        return ResponseEntity.ok(baseFeeRuleService.createBaseFeeRule(baseFeeRule));
    }


    /**
     * Retrieves all base fee rules.
     *
     * @return A list of all base fee rules wrapped in a ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        return ResponseEntity.ok(baseFeeRuleService.getAllBaseFeeRules());
    }


    /**
     * Retrieves a base fee rule by its ID.
     *
     * @param id The ID of the base fee rule to retrieve.
     * @return The requested base fee rule if found, otherwise a 404 Not Found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        return baseFeeRuleService.getBaseFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Updates an existing base fee rule.
     *
     * @param id The ID of the base fee rule to update.
     * @param baseFeeRuleDetails The new details for the base fee rule.
     * @return The updated base fee rule wrapped in a ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRuleDetails) {
        return ResponseEntity.ok(baseFeeRuleService.updateBaseFeeRule(id, baseFeeRuleDetails));
    }


    /**
     * Deletes a base fee rule.
     *
     * @param id The ID of the base fee rule to delete.
     * @return An empty ResponseEntity indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
