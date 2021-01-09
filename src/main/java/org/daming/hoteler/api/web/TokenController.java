package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.service.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class TokenController {

    private ITokenService tokenService;

    @ApiOperation(value = "create", notes = "create token api")
    @PostMapping("token")
    public ResponseEntity<UserToken> createToken(@RequestHeader String username, @RequestHeader String password) {
        var userToken = tokenService.createToken(username, password);
        var response = new ResponseEntity<>(userToken, HttpStatus.OK);
        return response;
    }

    @ApiOperation(value = "verify", notes = "verify token api")
    @PutMapping("token")
    public ResponseEntity<String> verifyToken(@RequestHeader String accessToken) {
        tokenService.verifyToken(accessToken);
        var response = new ResponseEntity<>("ok", HttpStatus.OK);
        return response;
    }

    public TokenController(ITokenService tokenService) {
        super();
        this.tokenService = tokenService;
    }
}
