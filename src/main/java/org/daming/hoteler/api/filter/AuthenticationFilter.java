package org.daming.hoteler.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.regex.Pattern;

/**
 * AuthenticationFilter
 *
 * @author gming001
 * @create 2020-12-28 11:40
 **/
@Component
public class AuthenticationFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Pattern ignoreUrlPattern = Pattern.compile(".*(swagger|webjars|configuration|token|images|api-docs).*");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = asHttp(servletRequest);
        var response = asHttp(servletResponse);
        var in = Instant.now();
        try {
            // Todo do filter
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
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
