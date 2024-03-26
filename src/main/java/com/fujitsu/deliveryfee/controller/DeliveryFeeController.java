package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.dto.DeliveryFeeRequest;
import com.fujitsu.deliveryfee.service.DeliveryFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-fee")
@Tag(name = "Delivery fee calculator")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    @Autowired
    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    @Operation(
            description = "Calculate the delivery fee based on the city and vehicle type."
    )
    @GetMapping("/{city}/{vehicleType}")
    public ResponseEntity<?> calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicleType) {
        try {
            city = city.toLowerCase();
            vehicleType = vehicleType.toLowerCase();
            double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType);
            return ResponseEntity.ok(fee);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Log the exception or return its message for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Operation(
            description = "Calculate the delivery fee based on the request parameters."
    )
    @PostMapping
    public ResponseEntity<?> calculateDeliveryFeePost(@RequestBody DeliveryFeeRequest request) {
        try {
            String city = request.getCity().toLowerCase();
            String vehicleType = request.getVehicleType().toLowerCase();
            double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType);
            return ResponseEntity.ok(fee);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Log the exception or return its message for debugging
            e.printStackTrace(); // Consider proper logging in production
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }





}
