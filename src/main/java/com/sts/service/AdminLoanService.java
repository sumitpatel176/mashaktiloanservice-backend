package com.sts.service;

import java.util.List;

import com.sts.entity.LoanApplication;
import com.sts.entity.StaffUser;

public interface AdminLoanService {

	public StaffUser registerNewStaff(StaffUser user);
	public List<LoanApplication> fetchAllApplication();
	public void deleteApplication(Long id);
	public void updateStatus(Long id, String status);
}
