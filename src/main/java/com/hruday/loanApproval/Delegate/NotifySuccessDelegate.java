package com.hruday.loanApproval.Delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("notifySuccess")
public class NotifySuccessDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Application Successful. Process ID: "+execution.getProcessInstanceId());
    }
}
