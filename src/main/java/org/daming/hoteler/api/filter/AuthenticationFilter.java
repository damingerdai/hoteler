package org.daming.hoteler.api.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.pojo.HotelerContext;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * AuthenticationFilter
 *
 * @author gming001
 * @create 2020-12-28 11:40
 **/
@Deprecated
// @Component
public class AuthenticationFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Pattern ignoreUrlPattern = Pattern.compile(".*(swagger|webjars|configuration|token|dev|ping|images|api-docs|html|js|css|svg|ico).*");

    private IUserService userService;

    private ISecretPropService secretPropService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = asHttp(servletRequest);
        var response = asHttp(servletResponse);
        var in = Instant.now();
        try {
            var requestUrl = request.getRequestURI();
           logger.debug("url: " + requestUrl + "\t" + isFilter(requestUrl));
            if (!isFilter(requestUrl) && !requestUrl.contains("token")) {
                logger.info("verify url2: " + requestUrl );
                verifyHttpHeaders(request);
                var context = new HotelerContext();
                ThreadLocalContextHolder.put(context);
                context.setIn(in);
                context.setRequestId(UUID.randomUUID().toString());
                verifyToken(request, context);
            } else {
                logger.info("url: " + requestUrl + " is ignored");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException ex) {
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
        } catch (HotelerException ex) {
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }

    private void verifyToken(HttpServletRequest httpRequest, HotelerContext context) {
        final String requestTokenHeader = httpRequest.getHeader("Authorization");
        if (Objects.isNull(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
            throw ExceptionBuilder.buildException(600002, "访问拒绝.");
        }
        var accessToken = requestTokenHeader.substring(7);
        if (!StringUtils.hasText(accessToken)) {
            throw ExceptionBuilder.buildException(600002, "访问拒绝.");
        }
        context.setAccessToken(accessToken);
        var key = JwtUtil.generalKey(this.secretPropService.getKey());
        var claims = JwtUtil.parseJwt(accessToken, key);
        var subject =  claims.getSubject();
        var username = subject.split("@")[1];
        var user = this.userService.getUserByUsername(username);
        context.setUser(user);
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest)request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse)response;
    }

    private void verifyHttpHeaders(HttpServletRequest request) {
        // TODO check the required thhp headers paramters. if not exist then throw exception
    }

    private boolean isFilter(String url) {
        // Environment environment =
        if ("/".equals(url)) {
            return true;
        }
        var pattern = "^/api/v\\d/(?!.*?token).*$";
        var r = Pattern.compile(pattern);
        return !r.matcher(url).matches();
    }

    private Exception buildException(String message) {
        return new Exception(message);
    }

    public AuthenticationFilter(IUserService userService, ISecretPropService secretPropService) {
        super();
        this.userService = userService;
        this.secretPropService = secretPropService;
    }
}
