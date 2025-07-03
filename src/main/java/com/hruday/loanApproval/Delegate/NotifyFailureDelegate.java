package com.hruday.loanApproval.Delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("notifyFailure")
public class NotifyFailureDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        System.out.println("Loan Application Failed. Process ID: " + execution.getProcessInstanceId());
        System.out.println("Reason: " + execution.getVariable("failureReason"));
    }
}
