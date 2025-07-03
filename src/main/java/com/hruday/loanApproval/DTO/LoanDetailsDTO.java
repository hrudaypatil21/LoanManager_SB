package com.hruday.loanApproval.DTO;

import java.util.Objects;

public class LoanDetailsDTO {
    private String taskId;
    private long accountId;
    private double salary;
    private double loanAmount;
    private double interestRate;

    public LoanDetailsDTO() {
    }

    public LoanDetailsDTO(String taskId, long accountId, double salary, double loanAmount, double interestRate) {
        this.taskId = taskId;
        this.accountId = accountId;
        this.salary = salary;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
    }

    // Getters and Setters
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDetailsDTO that = (LoanDetailsDTO) o;
        return accountId == that.accountId &&
                Double.compare(that.salary, salary) == 0 &&
                Double.compare(that.loanAmount, loanAmount) == 0 &&
                Double.compare(that.interestRate, interestRate) == 0 &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, accountId, salary, loanAmount, interestRate);
    }

    @Override
    public String toString() {
        return "LoanDetailsDTO{" +
                "taskId='" + taskId + '\'' +
                ", accountId=" + accountId +
                ", salary=" + salary +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                '}';
    }
}