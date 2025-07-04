package com.hruday.loanApproval.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "loans", schema = "loan_management")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "loan_period")
    private int loanPeriod;

    @Column(name = "emi")
    private double emi;

    @Column(name = "approval_date")
    private LocalDate approvalDate = LocalDate.now();

    @Column(name = "finish_date")
    private LocalDate finishDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    // equals(), hashCode() and toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Double.compare(loan.loanAmount, loanAmount) == 0 &&
                Double.compare(loan.interestRate, interestRate) == 0 &&
                loanPeriod == loan.loanPeriod &&
                Double.compare(loan.emi, emi) == 0 &&
                Objects.equals(id, loan.id) &&
                Objects.equals(accountId, loan.accountId) &&
                Objects.equals(approvalDate, loan.approvalDate) &&
                Objects.equals(finishDate, loan.finishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, loanAmount, interestRate, loanPeriod, emi, approvalDate, finishDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", loanPeriod=" + loanPeriod +
                ", emi=" + emi +
                ", approvalDate=" + approvalDate +
                ", finishDate=" + finishDate +
                '}';
    }
}