package org;


public class Customer {
    private String id;
    private String lastName;
    private String firstName;
    private String mobilePhone;
    private String email;
    // Constructor
    public Customer() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                "First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Email='" + email + '\'' +
                ", Mobile Phone='" + mobilePhone + '\'' +
                '}';
    }
}