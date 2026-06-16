package com.sts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "enter name!")
    private String customerName;
    @NotBlank(message = "Requiered Phone Number!")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "🚨 Kripya ek sahi 10-digit ka mobile number dalein!")
    private String phoneNumber;
    @NotBlank(message = "🚨 Loan type select karna zaroori hai!")
    private String loanType;
    @NotNull(message = "🚨 Loan amount dalna zaroori hai!")
    @Min(value = 1, message = "🚨 Loan amount 0 se zyada hona chahiye!")
    private Double loanAmount;
    @NotNull(message = "🚨 Monthly income dalna zaroori hai!")
    @Min(value = 1, message = "🚨 Monthly income 0 se zyada honi chahiye!")
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
    
    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}