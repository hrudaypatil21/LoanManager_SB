package com.hruday.loanApproval;

import com.hruday.loanApproval.Delegate.ExecutionDelegate;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class LoanProcessService {

    @Autowired
            private LoanCalc loanCalc;

    Logger logger = Logger.getLogger(ExecutionDelegate.class.getName());
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

    public LoanProcessService(RuntimeService runtimeService,
                              TaskService taskService,
                              IdentityService identityService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.identityService = identityService;
    }

    public String startProcess(String username) {
        identityService.setAuthenticatedUserId(username);

        Map<String, Object> vars = new HashMap<>();
        vars.put("username", username);

        ProcessInstance instance = runtimeService.startProcessInstanceByKey("loanApplyProcess", vars);

        Task task =taskService.createTaskQuery()
                .processInstanceId(instance.getId())
                .singleResult();
        return task != null ? task.getId() : null;
    }

    public String completeLoanForm(String taskId, Long accountId, double salary, double loanAmount, double interestRate) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found for ID: " + taskId);
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("accountId", accountId);
        variables.put("salary", salary);
        variables.put("loanAmount", loanAmount);
        variables.put("interestRate", interestRate);


        try {
            taskService.complete(taskId, variables);

            ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();

            if(instance == null) {
                return "Loan not eligible";
            }

            Task nextTask = taskService.createTaskQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .active()
                    .singleResult();

            return nextTask != null ? nextTask.getId() : "Process completed";
        } catch (Exception e) {
            return "Loan rejected: " + e.getMessage();
        }
    }

    public String approveLoan(String taskId, boolean approved) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if(task==null) {
            throw new RuntimeException("Approval task not found for ID: " + taskId);
        }

        Map<String, Object> vars = new HashMap<>();
        vars.put("loanApproved", approved);

        taskService.complete(taskId, vars);

        Task nextTask = taskService.createTaskQuery()
                .processInstanceId(task.getProcessInstanceId())
                .active()
                .singleResult();

        return "\nProcess completed";
    }

}