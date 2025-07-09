package com.hruday.loanApproval;

import org.springframework.stereotype.Component;

@Component
public class LoanCalc {
    private static final int MIN_TERM_MONTHS = 12;
    private static final int MAX_TERM_MONTHS = 180;
    private static final double MAX_EMI_RATIO = 0.4;

    public int calcOptimalPeriod(double salary, double loanAmount, double interestRate) {
        validateInputs(salary, loanAmount, interestRate);
        double monthlyRate = interestRate / 100 / 12;

        for (int n = MIN_TERM_MONTHS; n <= MAX_TERM_MONTHS; n++) {
            double emi = calculateEmi(loanAmount, monthlyRate, n);
            if (emi <= salary * MAX_EMI_RATIO) {
                return n;
            }
        }
        throw new RuntimeException(String.format(
                "\nNo feasible term found. Loan amount ₹%.2f at %.2f%% exceeds %.0f%% of salary ₹%.2f",
                loanAmount, interestRate, MAX_EMI_RATIO*100, salary
        ));
    }

    public double calcEmi(double salary, double loanAmount, double interestRate) {
        validateInputs(salary, loanAmount, interestRate);
        double monthlyRate = interestRate / 100 / 12;

        for (int n = MIN_TERM_MONTHS; n <= MAX_TERM_MONTHS; n++) {
            double emi = calculateEmi(loanAmount, monthlyRate, n);
            if (emi <= salary * MAX_EMI_RATIO) {
                System.out.printf("Valid EMI: ₹%.2f for %d months%n", emi, n);
                return emi;
            }
        }
        throw new RuntimeException("No EMI found within affordable range");
    }

    private double calculateEmi(double principal, double monthlyRate, int months) {
        return principal * monthlyRate * Math.pow(1 + monthlyRate, months)   //EMI = [P × r × (1+r)^n]/[(1+r)^n-1]
                / (Math.pow(1 + monthlyRate, months) - 1);
    }

    private void validateInputs(double salary, double loanAmount, double interestRate) {
        if (salary <= 0 || loanAmount <= 0 || interestRate <= 0) {
            throw new IllegalArgumentException(
                    String.format("Invalid inputs - Salary: ₹%.2f, Loan: ₹%.2f, Rate: %.2f%%",
                            salary, loanAmount, interestRate)
            );
        }
    }
}
