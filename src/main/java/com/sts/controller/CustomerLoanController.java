package com.sts.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.entity.LoanApplication;
import com.sts.service.CustomerLoanService;

@RestController
@RequestMapping("/api/public/loans")
@CrossOrigin(origins = "https://mashaktiloanservice-frontend-gray.vercel.app")
public class CustomerLoanController {
	
	@Autowired
	private CustomerLoanService customerService;
	
	@PostMapping("/submit")
	public ResponseEntity<?> submitForm(@RequestBody LoanApplication application){
		customerService.applyForLoan(application);
		return ResponseEntity.ok(Map.of(
	            "status", "success", 
	            "message", "🎉 Application successfully receive ho gayi hai!"
	        ));
	}

}
