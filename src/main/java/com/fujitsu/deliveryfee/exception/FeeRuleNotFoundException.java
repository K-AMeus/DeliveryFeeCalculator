package com.fujitsu.deliveryfee.exception;



/**
 * Custom exception thrown when a fee rule is not found by its ID
 */
public class FeeRuleNotFoundException extends RuntimeException {
    public FeeRuleNotFoundException(Long id) {
        super("Fee rule not found for id: " + id);
    }
}

