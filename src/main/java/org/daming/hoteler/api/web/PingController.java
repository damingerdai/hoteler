package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.pojo.annontaions.RateLimiter;
import org.daming.hoteler.pojo.enums.LimitType;
import org.daming.hoteler.service.IPingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ping Controller")
@RestController
public class PingController {

    private final IPingService pingService;

    @Operation(summary = "ping接口")
    @RequestMapping("ping")
    // @RateLimiter(time = 5,count = 3,limitType = LimitType.IP)
    public String Ping() {
        return this.pingService.ping();
    }

    public PingController(IPingService pingService) {
        this.pingService = pingService;
    }
}
