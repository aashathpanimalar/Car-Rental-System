package org.carrental.entity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Payment {
    private int paymentID;
    private int leaseID;
    private Date date;
    private double amount;

    public Payment() {}

    public Payment(int paymentID, int leaseID, Date date, double amount) {
        this.paymentID = paymentID;
        this.leaseID = leaseID;
        this.date = date;
        this.amount = amount;
    }
    // Core calculation method using Car and lease object
    public void calculateAmount(Car car, Lease lease) {
        if (car == null || lease == null || lease.getStartDate() == null || lease.getEndDate() == null) {
            throw new IllegalArgumentException("Car and Lease with valid dates must be provided");
        }

        long diffInMillis = lease.getEndDate().getTime() - lease.getStartDate().getTime();

        // when endDate is before startDate
        if (diffInMillis < 0) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        //internally TimeUnit converts milliseconds to no. of days=> numberOfDays = diffInMillis / (1000 * 60 * 60 * 24)
        long numberOfDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        // Include both start and end date if you want full-day inclusive count
        if (numberOfDays == 0) numberOfDays = 1; // at least 1 day
        else numberOfDays += 1;

        this.amount = car.getDailyRate() * numberOfDays;
    }



    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", leaseID=" + leaseID +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
