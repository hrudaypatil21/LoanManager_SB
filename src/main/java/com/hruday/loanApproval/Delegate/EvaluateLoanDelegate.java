package com.hruday.loanApproval.Delegate;

import com.hruday.loanApproval.AccountRepository;
import com.hruday.loanApproval.Entity.Account;
import com.hruday.loanApproval.LoanCalc;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("evaluateEligibility")
public class EvaluateLoanDelegate implements JavaDelegate {

    @Autowired
    private LoanCalc loanCalc;

    @Autowired
    private AccountRepository accountRepository;
//name, occupation, salary, loan amount, period
    @Override
    public void execute(DelegateExecution execution) {

        Long accountId = (Long) execution.getVariable("accountId");
//        String occupation = (String) execution.getVariable("occupation");
        double salary = (double) execution.getVariable("salary");
        double loanAmount = (double) execution.getVariable("loanAmount");
//        int loanPeriod = (int) execution.getVariable("loanPeriod");
        double interestRate = (double) execution.getVariable("interestRate");

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Could not find account"));

//        if(!accountRepository.existsById(account.getId())) {
//            System.out.println("Invalid account");
//        }

//        double monthlyRate = interestRate / 12 / 100;
//        double numerator = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanPeriod);
//        double denominator = Math.pow(1 + monthlyRate, loanPeriod) - 1;
//        double emi = numerator / denominator;

        double emi = loanCalc.calcOptimalPeriod(salary, loanAmount, interestRate);
//        int loanPeriod = loanCalc.calcOptimalPeriod(salary, loanAmount, interestRate);
        boolean isEligible = emi <= salary * 0.45;

        //will implement returning emi later
//        execution.setVariable("emi", emi);
        execution.setVariable("isEligible", isEligible);

    }
}