package de.seifenarts.domain.entity;

public enum PaymentStatus {
    PENDING,        // Payment session created, customer has not completed the payment yet
    AUTHORIZED,     // Payment authorized by Klarna, funds are reserved but not captured
    CAPTURED,       // Payment successfully captured, funds have been transferred
    FAILED,         // Payment failed due to an error or rejection
    CANCELLED,      // Payment was cancelled before funds were captured
    REFUNDED        // Payment was refunded after being captured
}

