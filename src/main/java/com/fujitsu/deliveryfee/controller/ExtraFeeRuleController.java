package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import com.fujitsu.deliveryfee.service.ExtraFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Rest controller for managing extra fee rules.
 * Extra fee rules define additional fees applied to delivery costs based on specific conditions,
 * such as weather phenomena, which can affect the delivery process. This controller provides
 * CRUD operations for extra fee rules, enabling the dynamic adjustment of fee calculations
 * according to changing conditions.
 */
@RestController
@RequestMapping("/api/extra-fee-rules")
public class ExtraFeeRuleController {

    private final ExtraFeeRuleService extraFeeRuleService;

    @Autowired
    public ExtraFeeRuleController(ExtraFeeRuleService extraFeeRuleService) {
        this.extraFeeRuleService = extraFeeRuleService;
    }


    /**
     * Creates a new extra fee rule in the system.
     *
     * @param extraFeeRule The extra fee rule to be created.
     * @return The created extra fee rule wrapped in a ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<ExtraFeeRule> createExtraFeeRule(@RequestBody ExtraFeeRule extraFeeRule) {
        return ResponseEntity.ok(extraFeeRuleService.createExtraFeeRule(extraFeeRule));
    }


    /**
     * Retrieves all extra fee rules currently defined in the system.
     *
     * @return A list of all extra fee rules wrapped in a ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<List<ExtraFeeRule>> getAllExtraFeeRules() {
        return ResponseEntity.ok(extraFeeRuleService.getAllExtraFeeRules());
    }


    /**
     * Retrieves a specific extra fee rule by its unique ID.
     *
     * @param id The ID of the extra fee rule to retrieve.
     * @return The requested extra fee rule if found, otherwise returns a 404 Not Found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> getExtraFeeRuleById(@PathVariable Long id) {
        return extraFeeRuleService.getExtraFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Updates an existing extra fee rule identified by its ID with new details.
     *
     * @param id The ID of the extra fee rule to update.
     * @param extraFeeRuleDetails The new details for the extra fee rule.
     * @return The updated extra fee rule wrapped in a ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> updateExtraFeeRule(@PathVariable Long id, @RequestBody ExtraFeeRule extraFeeRuleDetails) {
        return ResponseEntity.ok(extraFeeRuleService.updateExtraFeeRule(id, extraFeeRuleDetails));
    }


    /**
     * Deletes an extra fee rule from the system based on its ID.
     *
     * @param id The ID of the extra fee rule to delete.
     * @return An empty ResponseEntity indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtraFeeRule(@PathVariable Long id) {
        extraFeeRuleService.deleteExtraFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
