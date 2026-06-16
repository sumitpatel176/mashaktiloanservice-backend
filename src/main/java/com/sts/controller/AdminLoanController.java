package com.sts.controller;

import com.sts.entity.LoanApplication;
import com.sts.service.AdminLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/loans")

public class AdminLoanController {

    @Autowired
    private AdminLoanService adminService;

   
    @GetMapping("/all")
    public ResponseEntity<List<LoanApplication>> getAllData() {
        List<LoanApplication> list = adminService.fetchAllApplication();
        return ResponseEntity.ok(list);
    }

    // 🔥 2. NAYA METHOD: Status Update Karne Ke Liye (Approve / Reject)
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateLoanStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            adminService.updateStatus(id, status);
            return ResponseEntity.ok(Map.of(
                "status", "success", 
                "message", "Application status updated successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error", 
                "message", "Status update fail: " + e.getMessage()
            ));
        }
    }

    // 3. Data delete karne ke liye
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeData(@PathVariable Long id) {
        adminService.deleteApplication(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Application deleted!"));
    }
}