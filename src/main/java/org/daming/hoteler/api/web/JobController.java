package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.task.CustomerTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gming001
 * @version 2023-05-23 13:02
 */
@Tag(name = "Job Controller")
@RestController
@RequestMapping("api/v1/job")
public class JobController {

    private CustomerTask customerTask;

    @GetMapping("update-crpyto-customer-id")
    public String updateCrpytoCustomerId() {
        this.customerTask.updateCrpytoCustomerId();
        return "pong";
    }

    public JobController(CustomerTask customerTask) {
        this.customerTask = customerTask;
    }
}
