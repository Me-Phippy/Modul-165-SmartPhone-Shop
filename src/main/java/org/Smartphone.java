package org;

import java.util.List;
import java.math.BigDecimal;

public class Smartphone {
    private String id;
    private String brand;
    private String model;
    private BigDecimal price;
    private int ramGB;
    private double screenSizeInches;
    private int storageGB;
    private String operatingSystem;
    private String osVersion;
    private String resolution;
    private int processorCores;
    private int batteryCapacityMAh;
    private List<String> connectivity;
    private String mobileStandard;

    // Constructor
    public Smartphone() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getRamGB() {
        return ramGB;
    }

    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

    public double getScreenSizeInches() {
        return screenSizeInches;
    }

    public void setScreenSizeInches(double screenSizeInches) {
        this.screenSizeInches = screenSizeInches;
    }

    public int getStorageGB() {
        return storageGB;
    }

    public void setStorageGB(int storageGB) {
        this.storageGB = storageGB;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getProcessorCores() {
        return processorCores;
    }

    public void setProcessorCores(int processorCores) {
        this.processorCores = processorCores;
    }

    public int getBatteryCapacityMAh() {
        return batteryCapacityMAh;
    }

    public void setBatteryCapacityMAh(int batteryCapacityMAh) {
        this.batteryCapacityMAh = batteryCapacityMAh;
    }

    public List<String> getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(List<String> connectivity) {
        this.connectivity = connectivity;
    }

    public String getMobileStandard() {
        return mobileStandard;
    }

    public void setMobileStandard(String mobileStandard) {
        this.mobileStandard = mobileStandard;
    }



    @Override
    public String toString() {
        return "Smartphone{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", ramGB=" + ramGB +
                ", screenSizeInches=" + screenSizeInches +
                ", storageGB=" + storageGB +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", resolution='" + resolution + '\'' +
                ", processorCores=" + processorCores +
                ", batteryCapacityMAh=" + batteryCapacityMAh +
                ", connectivity=" + connectivity +
                ", mobileStandard='" + mobileStandard + '\'' +
                '}';
    }
}
