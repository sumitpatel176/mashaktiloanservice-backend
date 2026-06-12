package com.sts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sts.entity.LoanApplication;
import com.sts.repository.LoanApplicationRepository;

@Service
public class CustomerLoanServiceImp implements CustomerLoanService {

	@Autowired
	private LoanApplicationRepository loanapplication ;
	
	@Override
	public LoanApplication applyForLoan(LoanApplication application) {
		
		return loanapplication.save(application);
	}

}
