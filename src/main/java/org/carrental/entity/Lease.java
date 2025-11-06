package org.carrental.entity;

import java.util.Date;

public class Lease {
    private int leaseID;
    private int customerID;
    private int carID;
    private Date startDate;
    private Date endDate;
    private String type;

    public Lease(int leaseID, int customerID, int carID, Date startDate, Date endDate, String type) {
        this.leaseID = leaseID;
        this.customerID = customerID;
        this.carID = carID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public Lease() {}

    public int getLeaseID() { return leaseID; }
    public void setLeaseID(int leaseID) { this.leaseID = leaseID; }

    // NOTE: standardized method names to getCarID / setCarID
    public int getCarID() { return carID; }
    public void setCarID(int carID) { this.carID = carID; }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Lease{" +
                "type='" + type + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerID=" + customerID +
                ", carID=" + carID +
                ", leaseID=" + leaseID +
                '}';
    }
}
