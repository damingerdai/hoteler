package org.daming.hoteler.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.pojo.ApiError;
import org.daming.hoteler.pojo.HotelerContext;
import org.daming.hoteler.pojo.response.ErrorResponse;
import org.daming.hoteler.service.ITokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    private ITokenService tokenService;

    private Pattern ignoreUrlPattern = Pattern.compile(".*(swagger|webjars|configuration|token|dev|ping|images|api-docs|html|js|css|svg|ico).*");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestUrl = request.getRequestURI();
        if (isFilter(requestUrl) || requestUrl.contains("token")){
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(requestUrl);
        String token = getToken(request);
        if(StringUtils.hasLength(token)){
            var userInfo = this.tokenService.verifyToken(token);
            if(Objects.isNull(userInfo)){
                this.render(request, response, new ErrorResponse(new ApiError("ERROR-600002", "访问拒绝")));
                return;
            }
            if (StringUtils.hasLength(userInfo.getUsername()) &&  SecurityContextHolder.getContext().getAuthentication() == null){
                // 如果没过期，保持登录状态
                // 将用户信息存入 authentication，方便后续校验
                Set<GrantedAuthority> grantedAuthorities = userInfo.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+ role.getName().trim().toUpperCase()))
                        .collect(Collectors.toSet());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, grantedAuthorities);
                // SecurityContextHolder 权限验证上下文
                SecurityContext context = SecurityContextHolder.getContext();
                // 指示用户已通过身份验证
                context.setAuthentication(authentication);

            }
        }
        // 继续下一个过滤器
        filterChain.doFilter(request, response);
    }
    /**
     * 从header或者参数中获取token
     * @return token
     */
    public String getToken(HttpServletRequest request){
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

    private  void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
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

    public SecurityAuthTokenFilter(AuthenticationManager authenticationManager, ITokenService tokenService) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }
}
