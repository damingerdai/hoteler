package org.daming.hoteler.web;

import org.daming.hoteler.pojo.UserToken;
import org.daming.hoteler.service.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private ITokenService tokenService;

    public ResponseEntity<UserToken> createToken(@RequestHeader String username, @RequestHeader String password) {
        var userToken = tokenService.createToken(username, password);
        var response = new ResponseEntity<>(userToken, HttpStatus.OK);
        return response;
    }

    public TokenController(ITokenService tokenService) {
        super();
        this.tokenService = tokenService;
    }
}
