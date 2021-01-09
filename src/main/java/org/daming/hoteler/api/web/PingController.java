package org.daming.hoteler.api.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @RequestMapping("ping")
    public String Ping() {
        return "pong";
    }
}
