package org.daming.hoteler.api.web;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.JobInfo;
import org.daming.hoteler.pojo.request.AddPingJobTaskRequest;
import org.daming.hoteler.pojo.response.ListResponse;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IQuartzService;
import org.daming.hoteler.task.CustomerTask;
import org.daming.hoteler.task.job.CheckInTimeEventJob;
import org.daming.hoteler.task.job.PingJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author gming001
 * @version 2023-05-23 13:02
 */
@Tag(name = "Job Controller")
@RestController
@RequestMapping("api/v1/job")
public class JobController {

    private final CustomerTask customerTask;

    private final IQuartzService quartzService;

    private final IErrorService errorService;

    @GetMapping("update-crpyto-customer-id")
    public String updateCrpytoCustomerId() {
        this.customerTask.updateCrpytoCustomerId();
        return "pong";
    }

    @Operation(summary = "add ping job", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("add-ping-task")
    public String addPingTask(@RequestBody AddPingJobTaskRequest request) {
        try {
            var runDateTime = request.getRunDateTime();
            var zoneId = ZoneOffset.systemDefault();
            var localZonedDateTime = runDateTime.withZoneSameInstant(ZoneOffset.systemDefault());

            var triggerName = "ping-" + zoneId + "-" + localZonedDateTime.toInstant();
            var cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                    .withSecond(FieldExpressionFactory.on(localZonedDateTime.getSecond()))
                    .withMinute(FieldExpressionFactory.on(localZonedDateTime.getMinute()))
                    .withHour(FieldExpressionFactory.on(localZonedDateTime.getHour()))
                    .withDoM(FieldExpressionFactory.on(localZonedDateTime.getDayOfMonth()))
                    .withMonth(FieldExpressionFactory.on(localZonedDateTime.getMonth().getValue()))
                    .withDoW(FieldExpression.questionMark())
                    .withYear(FieldExpressionFactory.on(localZonedDateTime.getYear()))
                    .instance();

            var cronExpression = cron.asString();
            LoggerManager.getCommonLogger().info("add ping job: trigger {} -> cron {}", triggerName, cronExpression);
            this.quartzService.addJob(triggerName, "ping", cronExpression, PingJob.class);
            return "success";
        } catch (Exception ex) {
            throw this.errorService.createHotelerSystemException(ex.getMessage(), ex);
        }

    }

    @Operation(summary = "get all quartz jobs", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("jobinfos")
    public ListResponse<JobInfo> listQuartzJobs() throws HotelerException {
        try {
            var jobs = this.quartzService.listJob();
            return new ListResponse<>(jobs);
        } catch (Exception ex) {
            throw this.errorService.createHotelerSystemException(ex.getMessage(), ex);
        }
    }

    public JobController(CustomerTask customerTask, IQuartzService quartzService, IErrorService errorService) {
        super();
        this.customerTask = customerTask;
        this.quartzService = quartzService;
        this.errorService = errorService;
    }
}
