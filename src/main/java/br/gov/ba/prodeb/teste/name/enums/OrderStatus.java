package br.gov.ba.prodeb.teste.name.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED;

    /**
     * Converts a string to an OrderStatus enum.
     *
     * @param status the string representation of the order status
     * @return the corresponding OrderStatus enum
     * @throws IllegalArgumentException if the string does not match any OrderStatus
     */
    @JsonCreator
    public static OrderStatus fromString(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }
}
