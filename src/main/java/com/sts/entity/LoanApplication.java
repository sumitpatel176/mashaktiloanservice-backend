package com.sts.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String phoneNumber;
    private String loanType;
    private Double loanAmount;
    private Double monthlyIncome;
    
    // 🔥 1. Naya status variable (Default value PENDING set kar di hai)
    private String applicationStatus = "PENDING"; 

    // --- Getters and Setters ---
    public Long getId(){
    	return id;
    	}
    public void setId(Long id) { 
    	this.id = id; 
    	}
    public String getCustomerName() {
    	return customerName; 
    	}
    public void setCustomerName(String customerName) {
    	this.customerName = customerName;
    	}
    public String getPhoneNumber() {
    	return phoneNumber; 
    	}
    public void setPhoneNumber(String phoneNumber) { 
    	this.phoneNumber = phoneNumber;
    	}
    public String getLoanType() {
    	return loanType; 
    	}
    public void setLoanType(String loanType) {
    	this.loanType = loanType;
    	}
    public Double getLoanAmount() {
    	return loanAmount; 
    	}
    public void setLoanAmount(Double loanAmount) {
    	this.loanAmount = loanAmount; 
    	}
    public Double getMonthlyIncome() {
    	return monthlyIncome;
    	}
    public void setMonthlyIncome(Double monthlyIncome) { 
    	this.monthlyIncome = monthlyIncome; 
    	}

    // 🔥 2. Naya Getter Method
    public String getApplicationStatus() {
        return applicationStatus;
    }

    // 🔥 3. Naya Setter Method (Isse aapki ServiceImpl ki error khatam ho jayegi)
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}