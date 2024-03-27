package com.fujitsu.deliveryfee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String conditionType; // e.g., "Temperature", "WindSpeed"
    private String conditionValue; // e.g., "<-10", "10-20"
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
