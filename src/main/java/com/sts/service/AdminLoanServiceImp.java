package com.sts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sts.entity.LoanApplication;
import com.sts.entity.StaffUser;
import com.sts.repository.LoanApplicationRepository;
import com.sts.repository.StaffUserRepository;

@Service
public class AdminLoanServiceImp implements AdminLoanService {

	@Autowired
	private LoanApplicationRepository loanRepository;
	@Autowired
	private StaffUserRepository staffRepository;
	
	//@Autowired
   // private PasswordEncoder passwordEncoder;
	@Override
	public StaffUser registerNewStaff(StaffUser user) {
		//String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(user.getPassword());
        user.setRole("ROLE_ADMIN"); 
        return staffRepository.save(user);
	}

	@Override
	public List<LoanApplication> fetchAllApplication() {
		return loanRepository.findAll();
	}

	@Override
	public void deleteApplication(Long id) {
		loanRepository.deleteById(id);
		
	}

	// Baki saare @Autowired repository aur purane methods upar rahenge...

	@Override
	public void updateStatus(Long id, String status) {
	    // 1. Database se loan id ke hisab se form dhoodho, agar nahi mila toh error throw karo
	    LoanApplication loan = loanRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Application id " + id + " nahi mili!"));
	        
	    // 2. Loan ka status badlo (APPROVED ya REJECTED)
	    // ⚠️ Dhyan rakhna aapki LoanApplication entity me variable ka naam 'applicationStatus' hi ho
	    loan.setApplicationStatus(status); 
	    
	    // 3. Badla hua data wapas database me save (update) kar do
	    loanRepository.save(loan);
	}
}
