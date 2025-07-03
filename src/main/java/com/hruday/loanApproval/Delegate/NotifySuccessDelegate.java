package com.hruday.loanApproval.Delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("notifySuccess")
public class NotifySuccessDelegate implements JavaDelegate {

    Logger logger = Logger.getLogger(ExecutionDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) {
        logger.info("Application Successful. Process ID: "+execution.getProcessInstanceId());
    }
}
