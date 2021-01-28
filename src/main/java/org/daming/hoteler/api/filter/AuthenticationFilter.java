package org.daming.hoteler.api.filter;

import io.jsonwebtoken.Claims;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.HotelerContext;
import org.daming.hoteler.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Component
public class AuthenticationFilter extends GenericFilterBean {

    @Value("${secret.key}")
    private String secretKey;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Pattern ignoreUrlPattern = Pattern.compile(".*(swagger|webjars|configuration|token|images|api-docs|html|js|css|svg|ico).*");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = asHttp(servletRequest);
        var response = asHttp(servletResponse);
        System.out.println(request.getRequestURI());
        var in = Instant.now();
        try {
//            var accept = request.getHeader("Accept");
//            if (accept.contains("text/html") || accept.contains("image")) {
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
            if (!isFilter(request.getRequestURI())) {
                verifyHttpHeaders(request);
                var context = new HotelerContext();
                ThreadLocalContextHolder.put(context);
                context.setIn(in);
                context.setRequestId(UUID.randomUUID().toString());
                verifyToken(request, context);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (HotelerException ex) {
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            // response.sendRedirect("index.html");
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
        if (StringUtils.isEmpty(accessToken)) {
            throw ExceptionBuilder.buildException(600002, "访问拒绝.");
        }
        context.setAccessToken(accessToken);
        var key = JwtUtil.generalKey(secretKey);
        var claims = JwtUtil.parseJwt(accessToken, key);
        var subject =  claims.getSubject();
        System.out.println(subject);
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
        return ignoreUrlPattern.matcher(url).matches();
    }

    private Exception buildException(String message) {
        return new Exception(message);
    }

}
