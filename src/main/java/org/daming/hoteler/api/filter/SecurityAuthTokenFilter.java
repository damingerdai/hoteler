package org.daming.hoteler.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.pojo.HotelerContext;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.ITokenService;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author gming001
 * @version 2022-07-28 15:10
 */
public class SecurityAuthTokenFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ITokenService tokenService;

    private IUserService userService;

    private ISecretPropService secretPropService;


    private Pattern ignoreUrlPattern = Pattern.compile(".*(swagger|webjars|configuration|token|dev|ping|images|api-docs|html|js|css|svg|ico).*");

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        var request = asHttp(servletRequest);
        var response = asHttp(servletResponse);
        var in = Instant.now();
        try {
            var requestUrl = request.getRequestURI();
            logger.debug("url: " + requestUrl + "\t" + isFilter(requestUrl));
            var context = new HotelerContext();
            ThreadLocalContextHolder.put(context);
            context.setIn(in);
            context.setRequestId(UUID.randomUUID().toString());
            verifyToken(context, request);
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
            SecurityContextHolder.clearContext();
            if (logger.isErrorEnabled()) {
                logger.error("<{}> ErrorMsg: {}", ex.getClass().getSimpleName(), ex.getMessage());
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    /**
     * 从header或者参数中获取token
     *
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (Objects.isNull(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
            throw ExceptionBuilder.buildException(600002, "访问拒绝.");
        }
        var accessToken = requestTokenHeader.substring(7);
        if (!StringUtils.hasText(accessToken)) {
            throw ExceptionBuilder.buildException(600002, "访问拒绝.");
        }
        return accessToken;
    }

    private void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(new ObjectMapper().writeValueAsString(object));
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

    private void verifyToken(HotelerContext context, HttpServletRequest httpRequest) {
        final String requestTokenHeader = httpRequest.getHeader("Authorization");
        if (Objects.isNull(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
            // throw ExceptionBuilder.buildException(600002, "访问拒绝.");
            return;
        }
        var accessToken = requestTokenHeader.substring(7);
        if (!StringUtils.hasText(accessToken)) {
            // throw ExceptionBuilder.buildException(600002, "访问拒绝.");
            return;
        }
        context.setAccessToken(accessToken);
        var key = JwtUtil.generalKey(this.secretPropService.getKey());
        var claims = JwtUtil.parseJwt(accessToken, key);
        var subject = claims.getSubject();
        var username = subject.split("@")[1];
        var user = this.userService.getUserByUsername(username);
        context.setUser(user);
        verifyGrantedAuthority(user);
    }

    private void verifyGrantedAuthority(User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            return;
        }
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            return;
        }
        // 如果没过期，保持登录状态
        // 将用户信息存入 authentication，方便后续校验
        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().trim().toUpperCase()))
                .collect(Collectors.toSet());
        var authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, grantedAuthorities);
        // SecurityContextHolder 权限验证上下文
        var securityContext = SecurityContextHolder.getContext();
        // 指示用户已通过身份验证
        securityContext.setAuthentication(authentication);
    }

    public SecurityAuthTokenFilter(
            AuthenticationManager authenticationManager,
            ITokenService tokenService,
            IUserService userService,
            ISecretPropService secretPropService) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.userService = userService;
        this.secretPropService = secretPropService;
    }
}
