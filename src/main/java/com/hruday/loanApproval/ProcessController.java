package com.hruday.loanApproval;

import com.hruday.loanApproval.DTO.LoanDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    private LoanProcessService loanProcessService;

    @PostMapping("/start")
    public ResponseEntity<String> startProcess(@RequestParam String username) {
        String taskId = loanProcessService.startProcess(username);
        return ResponseEntity.ok().body(taskId);
    }

    @PostMapping("/submit-loan")
    public ResponseEntity<String> submitLoanTask(@RequestBody LoanDetailsDTO loanDetailsDTO) {
        String nextTaskId = loanProcessService.completeLoanForm(loanDetailsDTO.getTaskId(), loanDetailsDTO.getAccountId(), loanDetailsDTO.getSalary(), loanDetailsDTO.getLoanAmount(), loanDetailsDTO.getInterestRate());
        return ResponseEntity.ok().body("Submitted, next task ID: "+nextTaskId);
    }
}
