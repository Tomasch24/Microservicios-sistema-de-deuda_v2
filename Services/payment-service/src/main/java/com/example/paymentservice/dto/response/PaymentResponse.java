package com.example.paymentservice.dto.response;

import com.example.paymentservice.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponse(
        Long id,
        String debtId,
        BigDecimal amount,
        LocalDate paymentDate,
        String note,
        String createdAt) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getDebtId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getNote(),
                payment.getCreatedAt());
    }
}
