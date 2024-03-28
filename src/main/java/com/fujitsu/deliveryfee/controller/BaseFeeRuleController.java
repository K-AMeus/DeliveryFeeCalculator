package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.BaseFeeRule;
import com.fujitsu.deliveryfee.service.BaseFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * Rest controller for managing base fee rules.
 * Provides endpoints for CRUD operations on base fee rules, which define the foundational
 * fees applied for deliveries based on the city and vehicle type.
 */
@Tag(name = "Base Fee Rules", description = "CRUD operations for base fee rules")
@RestController
@RequestMapping("/api/base-fee-rules")
public class BaseFeeRuleController {

    private final BaseFeeRuleService baseFeeRuleService;

    @Autowired
    public BaseFeeRuleController(BaseFeeRuleService baseFeeRuleService) {
        this.baseFeeRuleService = baseFeeRuleService;
    }


    @Operation(summary = "Create Base Fee Rule",
            description = "Creates a new base fee rule for calculating delivery fees.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Base fee rule created successfully",
                            content = @Content(schema = @Schema(implementation = BaseFeeRule.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        return ResponseEntity.ok(baseFeeRuleService.createBaseFeeRule(baseFeeRule));
    }


    @Operation(summary = "Get All Base Fee Rules",
            description = "Retrieves a list of all base fee rules.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                            content = @Content(schema = @Schema(implementation = BaseFeeRule.class))),
            })
    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        return ResponseEntity.ok(baseFeeRuleService.getAllBaseFeeRules());
    }


    @Operation(summary = "Get Base Fee Rule by ID",
            description = "Retrieves a base fee rule by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found the base fee rule",
                            content = @Content(schema = @Schema(implementation = BaseFeeRule.class))),
                    @ApiResponse(responseCode = "404", description = "Base fee rule not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        return baseFeeRuleService.getBaseFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Update Base Fee Rule",
            description = "Updates an existing base fee rule identified by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Base fee rule updated successfully",
                            content = @Content(schema = @Schema(implementation = BaseFeeRule.class))),
                    @ApiResponse(responseCode = "404", description = "Base fee rule not found"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            })
    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRuleDetails) {
        return ResponseEntity.ok(baseFeeRuleService.updateBaseFeeRule(id, baseFeeRuleDetails));
    }


    @Operation(summary = "Delete Base Fee Rule",
            description = "Deletes an existing base fee rule identified by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Base fee rule deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Base fee rule not found")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
