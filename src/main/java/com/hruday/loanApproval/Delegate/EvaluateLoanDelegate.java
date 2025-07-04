package com.hruday.loanApproval.Delegate;

import com.hruday.loanApproval.AccountRepository;
import com.hruday.loanApproval.Entity.Account;
import com.hruday.loanApproval.LoanCalc;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("evaluateEligibility")
public class EvaluateLoanDelegate implements JavaDelegate {

    Logger logger = Logger.getLogger(ExecutionDelegate.class.getName());

    @Autowired
    private LoanCalc loanCalc;

    @Autowired
    private AccountRepository accountRepository;
//name, occupation, salary, loan amount, period
    @Override
    public void execute(DelegateExecution execution) {
        try {
            Long accountId = (Long) execution.getVariable("accountId");
            double salary = (double) execution.getVariable("salary");
            double loanAmount = (double) execution.getVariable("loanAmount");
            double interestRate = (double) execution.getVariable("interestRate");

            if (!accountRepository.existsById(accountId)) {
                throw new RuntimeException("Account not found: " + accountId);
            }

            double emi = loanCalc.calcEmi(salary, loanAmount, interestRate);
            int period = loanCalc.calcOptimalPeriod(salary, loanAmount, interestRate);
            boolean isEligible = emi <= salary * 0.45;

            execution.setVariable("emi", emi);
            execution.setVariable("isEligible", isEligible);

            logger.info("\nLoan details: \nAccount ID: "+accountId+"\nSalary: "+salary+"\nLoan Amount: "+loanAmount+"\nInterest Rate: "+interestRate+"\nEMI: "+emi+"\nPeriod: "+period+" months");

            if (!isEligible) {
                execution.setVariable("failureReason",
                        "EMI " + emi + " exceeds 45% of salary " + salary);
                throw new RuntimeException("Loan not eligible");

            }

        } catch (Exception e) {
            execution.setVariable("errorMessage", e.getMessage());
            throw e;
        }

    }
}