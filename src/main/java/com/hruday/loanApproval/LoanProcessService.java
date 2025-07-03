package com.hruday.loanApproval;

import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoanProcessService {

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

    // Constructor injection (replaces @RequiredArgsConstructor)
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

        taskService.complete(taskId, variables);

        return "Loan form submitted successfully.";
    }
}