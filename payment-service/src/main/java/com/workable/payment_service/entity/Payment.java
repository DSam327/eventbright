package com.workable.payment_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private Long bookingId;

    @Setter
    private Double amount;

    @Setter
    @Getter
    private String status; // SUCCESS / FAILED

    @Setter
    private LocalDateTime paymentTime;

    public Payment() {}

    // getters & setters
}
