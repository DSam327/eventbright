package com.workable.payment_service.controller;

import com.workable.payment_service.entity.Payment;
import com.workable.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping
    public Payment pay(@RequestBody Payment payment) {
        return service.processPayment(payment);
    }
}
