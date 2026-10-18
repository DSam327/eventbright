package com.workable.booking_service.dto;

public class BookingEvent {

    private Long bookingId;
    private Double amount;

    public BookingEvent() {}

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
