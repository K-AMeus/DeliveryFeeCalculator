package com.fujitsu.deliveryfee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Represents extra fee rules that adjust the delivery fee based on specific conditions.
 * These conditions can include temperature, wind speed and weather phenomenon.
 * This entity allows for dynamic fee adjustments to account for various external factors.
 */
@Entity
public class ExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String conditionType; // The type of condition (e.g., temperature, windSpeed, phenomenon)
    private String conditionValue; // // The value or range that triggers the fee adjustment (e.g., "<-10", "10-20", "thunder")
    private Double fee;

    public ExtraFeeRule() {
    }

    public ExtraFeeRule(Long id, String conditionType, String conditionValue, Double fee) {
        this.id = id;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public String getConditionType() {
        return conditionType;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public Double getFee() {
        return fee;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
