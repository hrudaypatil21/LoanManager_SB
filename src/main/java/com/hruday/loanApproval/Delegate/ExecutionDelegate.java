package com.hruday.loanApproval.Delegate;

import com.hruday.loanApproval.AccountRepository;
import com.hruday.loanApproval.Entity.Account;
import com.hruday.loanApproval.Entity.Loan;
import com.hruday.loanApproval.LoanCalc;
import com.hruday.loanApproval.LoanRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("loanExecution")
public class ExecutionDelegate implements JavaDelegate {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanCalc loanCalc;

    @Override
    public void execute(DelegateExecution execution) {

        Long accountId = (Long) execution.getVariable("accountId");
        double salary = (double) execution.getVariable("salary");
        double loanAmount = (double) execution.getVariable("loanAmount");
//        int loanPeriod = (int) execution.getVariable("loanPeriod");
        double interestRate = (double)  execution.getVariable("interestRate");

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Could not find account"));

        double emi = loanCalc.calcEmi(salary, loanAmount, interestRate);
        int loanPeriod = loanCalc.calcOptimalPeriod(salary, loanAmount, interestRate);

        //will implement returning emi later
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
        execution.setVariable("loanPeriod", loanPeriod  );
    }
}

//        execution.setVariable("emi", emi);

//        @Column(name = "account_id")
//        private Long accountId;
//
//        @Column(name = "loan_amount")
//        private double loanAmount;
//
//        @Column(name = "interest_rate")
//        private double interestRate;
//
//        @Column(name = "loan_period")
//        private int loanPeriod;
//
//        @Column(name = "emi")
//        private double emi;

