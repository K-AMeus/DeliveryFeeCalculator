package com.fujitsu.deliveryfee.controller;

import com.fujitsu.deliveryfee.dto.DeliveryFeeRequest;
import com.fujitsu.deliveryfee.service.DeliveryFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Double> calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicleType) {
            double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType);
            return ResponseEntity.ok(fee);

    }


    @Operation(
            description = "Calculate the delivery fee based on the request parameters."
    )
    @PostMapping
    public ResponseEntity<Double> calculateDeliveryFeePost(@RequestBody DeliveryFeeRequest request) {
        double fee = deliveryFeeService.calculateDeliveryFee(request.getCity(), request.getVehicleType());
            return ResponseEntity.ok(fee);

    }
}
