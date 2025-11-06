package org.carrental.entity;

public class Car {
    private int carID;
    private String make;
    private String model;
    private int year;
    private double dailyRate;
    private String status;
    private int passengerCapacity;
    private int engineCapacity;

    public Car() {}

    public Car(int carID, String make, String model, int year, double dailyRate, String status,
               int passengerCapacity, int engineCapacity) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
        this.status = status;
        this.passengerCapacity = passengerCapacity;
        this.engineCapacity = engineCapacity;
    }

   
    public int getCarID() { return carID; }
    public void setCarID(int carID) { this.carID = carID; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int passengerCapacity) { this.passengerCapacity = passengerCapacity; }

    public int getEngineCapacity() { return engineCapacity; }
    public void setEngineCapacity(int engineCapacity) { this.engineCapacity = engineCapacity; }

    @Override
    public String toString() {
        return "Car{" +
                "carID=" + carID +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", dailyRate=" + dailyRate +
                ", status='" + status + '\'' +
                ", passengerCapacity=" + passengerCapacity +
                ", engineCapacity=" + engineCapacity +
                '}';
    }


}
