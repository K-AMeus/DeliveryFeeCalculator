package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.model.ExtraFeeRule;
import com.fujitsu.deliveryfee.service.ExtraFeeRuleService;
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
 * Rest controller for managing extra fee rules.
 * Extra fee rules define additional fees applied to delivery costs based on specific conditions,
 * such as weather phenomena, which can affect the delivery process. This controller provides
 * CRUD operations for extra fee rules, enabling the dynamic adjustment of fee calculations
 * according to changing conditions.
 */
@Tag(name = "Extra Fee Rules", description = "Manage extra fee rules for adjusting delivery fees based on specific conditions.")
@RestController
@RequestMapping("/api/extra-fee-rules")
public class ExtraFeeRuleController {

    private final ExtraFeeRuleService extraFeeRuleService;

    @Autowired
    public ExtraFeeRuleController(ExtraFeeRuleService extraFeeRuleService) {
        this.extraFeeRuleService = extraFeeRuleService;
    }


    @Operation(summary = "Create Extra Fee Rule",
            description = "Creates a new extra fee rule for adjusting delivery fees based on conditions such as weather.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra fee rule created successfully",
                            content = @Content(schema = @Schema(implementation = ExtraFeeRule.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    @PostMapping
    public ResponseEntity<ExtraFeeRule> createExtraFeeRule(@RequestBody ExtraFeeRule extraFeeRule) {
        return ResponseEntity.ok(extraFeeRuleService.createExtraFeeRule(extraFeeRule));
    }


    @Operation(summary = "Get All Extra Fee Rules",
            description = "Retrieves all extra fee rules defined in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved all extra fee rules",
                            content = @Content(schema = @Schema(implementation = ExtraFeeRule.class)))
            })
    @GetMapping
    public ResponseEntity<List<ExtraFeeRule>> getAllExtraFeeRules() {
        return ResponseEntity.ok(extraFeeRuleService.getAllExtraFeeRules());
    }


    @Operation(summary = "Get Extra Fee Rule by ID",
            description = "Retrieves an extra fee rule by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found the extra fee rule",
                            content = @Content(schema = @Schema(implementation = ExtraFeeRule.class))),
                    @ApiResponse(responseCode = "404", description = "Extra fee rule not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> getExtraFeeRuleById(@PathVariable Long id) {
        return extraFeeRuleService.getExtraFeeRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Update Extra Fee Rule",
            description = "Updates an existing extra fee rule identified by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra fee rule updated successfully",
                            content = @Content(schema = @Schema(implementation = ExtraFeeRule.class))),
                    @ApiResponse(responseCode = "404", description = "Extra fee rule not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    @PutMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> updateExtraFeeRule(@PathVariable Long id, @RequestBody ExtraFeeRule extraFeeRuleDetails) {
        return ResponseEntity.ok(extraFeeRuleService.updateExtraFeeRule(id, extraFeeRuleDetails));
    }


    @Operation(summary = "Delete Extra Fee Rule",
            description = "Deletes an extra fee rule from the system based on its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extra fee rule deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Extra fee rule not found")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtraFeeRule(@PathVariable Long id) {
        extraFeeRuleService.deleteExtraFeeRule(id);
        return ResponseEntity.ok().build();
    }
}
