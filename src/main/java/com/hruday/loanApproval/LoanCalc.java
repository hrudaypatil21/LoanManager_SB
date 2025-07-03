package com.hruday.loanApproval;

import org.springframework.stereotype.Component;

@Component
public class LoanCalc {

    public int calcOptimalPeriod(double salary, double loanAmount, double interestRate) {

        if (salary <= 0 || loanAmount <= 0 || interestRate <= 0) {
            throw new IllegalArgumentException("Invalid input values");
        }

        double maxEmi = salary * 0.4;
        double monthlyRate = interestRate / 12 / 100;

        int optimalPeriod = -1;

        for (int n = 12; n <= 360; n++) {
            double numerator = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, n);
            double denominator = Math.pow(1 + monthlyRate, n) - 1;
            double emi = numerator / denominator;

            if (emi <= maxEmi) {
                optimalPeriod = n;
                break;
            }

            System.out.println("Optimal Loan Period: " + optimalPeriod + " months");
        }

        if (optimalPeriod == -1) {
            throw new RuntimeException("Loan amount too high for current salary and interest rate");
        }

        return optimalPeriod;
    }

    public double calcEmi(double salary, double loanAmount, double interestRate) {

// Max EMI = 40% of monthly salary
        double maxEmi = salary * 0.4;
        double monthlyRate = interestRate / 12 / 100;

// Start with min 12 months and go up to max 360 months (30 years)
        double finalEmi = -1;

        for (int n = 12; n <= 360; n++) {
            double numerator = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, n);
            double denominator = Math.pow(1 + monthlyRate, n) - 1;
            double emi = numerator / denominator;

            if (emi <= maxEmi) {
                finalEmi = emi;
                break;
            }
        }

        System.out.println("Estimated EMI: ₹" + String.format("%.2f", finalEmi));

        return finalEmi;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "account_id")
//    private Long accountId;
//
//    @Column(name = "loan_amount")
//    private double loanAmount;
//
//    @Column(name = "interest_rate")
//    private double interestRate;
//
//    @Column(name = "loan_period")
//    private int loanPeriod;
//
//    @Column(name = "emi")
//    private double emi;
//
//    @Column(name = "approval_date")
//    private LocalDate approvalDate = LocalDate.now();
//
//    @Column(name = "finish_date")
//    private LocalDate finishDate;

}
