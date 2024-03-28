package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.dto.DeliveryFeeRequest;
import com.fujitsu.deliveryfee.service.DeliveryFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * Rest controller for calculating delivery fees.
 * Provides endpoints to calculate delivery fees based on city, vehicle type, and optionally, time.
 */
@Tag(name = "Delivery Fee Calculator", description = "Endpoints for calculating delivery fees based on various conditions.")
@RestController
@RequestMapping("/delivery-fee")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    @Autowired
    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }



    @Operation(summary = "Calculate Delivery Fee",
            description = "Calculates the delivery fee based on city, vehicle type, and an optional date-time parameter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery fee calculated successfully",
                            content = @Content(schema = @Schema(implementation = Double.class))),
                    @ApiResponse(responseCode = "404", description = "City or vehicle type not supported")
            })
    @GetMapping("/{city}/{vehicleType}")
    public ResponseEntity<Double> calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicleType,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, dateTime != null ? dateTime : LocalDateTime.now());
        return ResponseEntity.ok(fee);
    }



    @Operation(summary = "Calculate Delivery Fee with Request Body",
            description = "Calculates the delivery fee based on provided request body containing city, vehicle type, and optional date-time.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery fee calculated successfully",
                            content = @Content(schema = @Schema(implementation = Double.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request body")
            })
    @PostMapping
    public ResponseEntity<Double> calculateDeliveryFeePost(@RequestBody DeliveryFeeRequest request) {
        double fee = deliveryFeeService.calculateDeliveryFee(request.getCity(), request.getVehicleType(), request.getDateTime());
        return ResponseEntity.ok(fee);
    }


}
