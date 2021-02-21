package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

@RestController
@RequestMapping("api/v1")
public class TokenController {

    private ITokenService tokenService;

    @ApiOperation(value = "create", notes = "create token api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "username", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "admin"),
            @ApiImplicitParam(name = "password", value = "password", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "12345")
    })
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
