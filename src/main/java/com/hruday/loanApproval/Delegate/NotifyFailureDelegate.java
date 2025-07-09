package com.hruday.loanApproval.Delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component("notifyFailure")
public class NotifyFailureDelegate implements JavaDelegate {

    Logger logger = Logger.getLogger(ExecutionDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) {
        String reason = (String) execution.getVariable("failureReason");
        Long accountId = (Long) execution.getVariable("accountId");

        logger.info("Loan application rejected for account: " + accountId);
        logger.info("Reason: " + (reason != null ? reason : "Not eligible/rejected"));
    }
}
