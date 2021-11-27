package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.response.UserTokenResponse;
import org.daming.hoteler.service.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token Controller")
@RestController
@RequestMapping("api/v1")
public class TokenController {

    private ITokenService tokenService;

    @Operation(summary = "创建token")
    @PostMapping("token")
    public UserTokenResponse createToken(@RequestHeader String username, @RequestHeader String password) {
        try {
            var userToken = tokenService.createToken(username, password);
            var response = new UserTokenResponse();
            response.setUserToken(userToken);
            return response;
        } catch (HotelerException ex) {
            LoggerManager.getApiLogger().error("HotelerException: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("Exception: " + ex.getMessage());
            throw ex;
        } finally {
            // TODO
        }

    }

    @Operation(summary = "更新token")
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
