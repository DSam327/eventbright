package com.workable.notification_service.dto;

public class PaymentEvent {

    private Long bookingId;
    private String status; // SUCCESS / FAILED

    public PaymentEvent() {}

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
