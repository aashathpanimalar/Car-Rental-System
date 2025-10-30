package org.carrental.entity;

import java.util.Date;

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
