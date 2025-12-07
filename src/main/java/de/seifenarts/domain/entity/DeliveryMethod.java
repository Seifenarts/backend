package de.seifenarts.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeliveryMethod {
    PICKUP, DELIVERY;

    @JsonCreator
    public static DeliveryMethod fromString(String value) {
        if (value == null) return null;
        return DeliveryMethod.valueOf(value.trim().toUpperCase());
    }
}


