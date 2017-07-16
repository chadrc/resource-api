package com.chadrc.resourceapi.models;

public class Address {
    private String city;
    private String state;
    private String address;
    private String zip;

    public Address() {

    }

    public Address(String city, String state, String address, String zip) {
        this.city = city;
        this.state = state;
        this.address = address;
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
