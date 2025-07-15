package com.hruday.loanApproval;

import com.hruday.loanApproval.DTO.LoanDetailsDTO;
import com.hruday.loanApproval.ExceptionHandler.ErrorResponse;
import com.hruday.loanApproval.ExceptionHandler.LoanIneligibleException;
//import com.hruday.loanApproval.ExceptionHandler.LoanRejectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        String nextTaskId = loanProcessService.completeLoanForm(
                loanDetailsDTO.getTaskId(),
                loanDetailsDTO.getAccountId(),
                loanDetailsDTO.getSalary(),
                loanDetailsDTO.getLoanAmount(),
                loanDetailsDTO.getInterestRate(),
                loanDetailsDTO.getPan());
        String responseMessage = String.format("\nLoan details: \nAccount ID: %d\nSalary: %.2f\nLoan Amount: %.2f\nInterest Rate: %.2f\nPan: %s", loanDetailsDTO.getAccountId(), loanDetailsDTO.getSalary(), loanDetailsDTO.getLoanAmount(), loanDetailsDTO.getInterestRate(), loanDetailsDTO.getPan());
        return ResponseEntity.ok().body("\nSubmitted, next task ID: " + nextTaskId + "\n"+responseMessage);
    }

    @PostMapping("/approve-loan")
    public ResponseEntity<String> approveLoan(@RequestParam String taskId, @RequestParam boolean approved) {
        String nextTaskId = loanProcessService.approveLoan(taskId, approved);
        String message = approved ? "\nLoan approved" : "\nLoan rejected";
        return ResponseEntity.ok().body("\n"+message + ", next task ID: " + nextTaskId);
    }

    @PostMapping("/document-verification")
    public ResponseEntity<List<Map<String,Object>>> documentVerification() {
        List<Map<String, Object>> results = loanProcessService.documentVerification();
        return ResponseEntity.ok(results);
    }

    @ExceptionHandler(value = LoanIneligibleException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handleLoanIneligibleException(LoanIneligibleException e) {
        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

//    @ExceptionHandler(value = LoanRejectException.class)
//    @ResponseStatus(HttpStatus.GONE)
//    public ErrorResponse handleLoanRejectedException(LoanRejectException e) {
//        return new ErrorResponse(HttpStatus.GONE.value(), e.getMessage());
//    }
}
