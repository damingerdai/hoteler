package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.response.UserTokenResponse;
import org.daming.hoteler.service.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token Controller")
@RestController
@RequestMapping("api/v1")
public class TokenController {

    private AuthenticationManager authenticationManager;
    private ITokenService tokenService;

    @Operation(summary = "创建token")
    @PostMapping("token")
    public UserTokenResponse createToken(@RequestHeader String username, @RequestHeader String password, HttpSession session) {
        try {
            var unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            var authenticate = authenticationManager.authenticate(unauthenticated);
            var userToken = tokenService.createToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            var response = new UserTokenResponse();
            response.setUserToken(userToken);
            return response;
        } catch (AuthenticationException ex) {
                LoggerManager.getApiLogger().error("AuthenticationException: " + ex.getMessage());
                throw ex;
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
    public UserTokenResponse verifyToken(@RequestHeader String accessToken) {
        try {
            var userToken = tokenService.refreshToken(accessToken);
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

    public TokenController(AuthenticationManager authenticationManager, ITokenService tokenService) {
        super();
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }
}
