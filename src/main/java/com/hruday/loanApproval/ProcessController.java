package com.hruday.loanApproval;

import com.hruday.loanApproval.DTO.LoanDetailsDTO;
import liquibase.pro.packaged.S;
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
        String responseMessage = String.format("\nLoan details: \nAccount ID: %d\nSalary: %.2f\nLoan Amount: %.2f\nInterest Rate: %.2f", loanDetailsDTO.getAccountId(), loanDetailsDTO.getSalary(), loanDetailsDTO.getLoanAmount(), loanDetailsDTO.getInterestRate());
        return ResponseEntity.ok().body("\nSubmitted, next task ID: " + nextTaskId + "\n"+responseMessage);
    }

    @PostMapping("/approve-loan")
    public ResponseEntity<String> approveLoan(@RequestParam String taskId, @RequestParam boolean approved) {
        String nextTaskId = loanProcessService.approveLoan(taskId, approved);
        String message = approved ? "\nLoan approved" : "\nLoan rejected";
        return ResponseEntity.ok().body("\n"+message + ", next task ID: " + nextTaskId);
    }
}
