package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ping Controller")
@RestController
public class PingController {


    @Operation(summary = "ping接口")
    @RequestMapping("ping")
    public String Ping() {
        return "pong";
    }
}
