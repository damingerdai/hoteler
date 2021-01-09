package org.daming.hoteler.api.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("dev/v1")
public class DevController {

    @Value("${profile}")
    private String profile;

    @GetMapping("spring/profile/active")
    public ResponseEntity<String> profile() {
        if (StringUtils.hasText(profile)) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("no profile", HttpStatus.NOT_FOUND);
        }
    }
}
