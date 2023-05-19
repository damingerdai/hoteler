package org.daming.hoteler.api.graphql;

import org.daming.hoteler.service.IPingService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller(value = "GraphqlPingController")
public class PingController {

    private final IPingService pingService;

    @QueryMapping
    public String ping() {
        return this.pingService.ping();
    }

    public PingController(IPingService pingService) {
        super();
        this.pingService = pingService;
    }
}
