package com.hruday.loanApproval.Delegate;

import com.hruday.loanApproval.AccountRepository;
import com.hruday.loanApproval.Entity.Loan;
import com.hruday.loanApproval.LoanCalc;
import com.hruday.loanApproval.LoanRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component("loanExecution")
public class ExecutionDelegate implements JavaDelegate {

    Logger logger = Logger.getLogger(ExecutionDelegate.class.getName());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanCalc loanCalc;

    @Override
    public void execute(DelegateExecution execution) {
        try {
            Long accountId = (Long) execution.getVariable("accountId");
            double salary = (double) execution.getVariable("salary");
            double loanAmount = (double) execution.getVariable("loanAmount");
//        int loanPeriod = (int) execution.getVariable("loanPeriod");
            double interestRate = (double) execution.getVariable("interestRate");

            if (!accountRepository.existsById(accountId)) {
                throw new RuntimeException("Account not found: " + accountId);
            }

            double emi = loanCalc.calcEmi(salary, loanAmount, interestRate);
            int loanPeriod = loanCalc.calcOptimalPeriod(salary, loanAmount, interestRate);

            Loan newLoan = new Loan();
            newLoan.setAccountId(accountId);
            newLoan.setLoanAmount(loanAmount);
            newLoan.setLoanPeriod(loanPeriod);
            newLoan.setInterestRate(interestRate);
            newLoan.setEmi(emi);
            newLoan.setApprovalDate(LocalDate.now());
            newLoan.setFinishDate(LocalDate.now().plusMonths(loanPeriod));

            loanRepository.save(newLoan);

            execution.setVariable("emi", emi);
            execution.setVariable("loanPeriod", loanPeriod);


            logger.info("Executing loan approval process");
        } catch (Exception e) {
            execution.setVariable("errorMessage", e.getMessage());
            throw e;
        }
    }
}


