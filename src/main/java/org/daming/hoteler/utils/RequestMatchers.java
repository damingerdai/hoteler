package org.daming.hoteler.utils;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilities for creating RequestMatcher instances.
 * Author: Rob Winch
 * @author gming001
 * @version 2022-11-25 22:39
 */
public final class RequestMatchers {

    private RequestMatchers() {
    }

    /**
     * Create a {@link List} of {@link AntPathRequestMatcher} instances.
     * @param httpMethod the {@link HttpMethod} to use or {@code null} for any
     * {@link HttpMethod}.
     * @param antPatterns the ant patterns to create {@link AntPathRequestMatcher}
     * from
     * @return a {@link List} of {@link AntPathRequestMatcher} instances
     */
    public static List<RequestMatcher> antMatchers(HttpMethod httpMethod, String... antPatterns) {
        String method = (httpMethod != null) ? httpMethod.toString() : null;
        return Arrays.stream(antPatterns).map((pattern) -> new AntPathRequestMatcher(pattern, method)).collect(Collectors.toList());
    }

    /**
     * Create a {@link List} of {@link AntPathRequestMatcher} instances that do not
     * specify an {@link HttpMethod}.
     * @param antPatterns the ant patterns to create {@link AntPathRequestMatcher}
     * from
     * @return a {@link List} of {@link AntPathRequestMatcher} instances
     */
    public static List<RequestMatcher> antMatchers(String... antPatterns) {
        return antMatchers(null, antPatterns);
    }

    /**
     * Create a {@link List} of {@link RegexRequestMatcher} instances.
     * @param httpMethod the {@link HttpMethod} to use or {@code null} for any
     * {@link HttpMethod}.
     * @param regexPatterns the regular expressions to create
     * {@link RegexRequestMatcher} from
     * @return a {@link List} of {@link RegexRequestMatcher} instances
     */
    public static List<RequestMatcher> regexMatchers(HttpMethod httpMethod, String... regexPatterns) {
        String method = (httpMethod != null) ? httpMethod.toString() : null;
        return Arrays.stream(regexPatterns).map(pattern ->new RegexRequestMatcher(pattern, method)).collect(Collectors.toList());
    }

    /**
     * Create a {@link List} of {@link RegexRequestMatcher} instances that do not
     * specify an {@link HttpMethod}.
     * @param regexPatterns the regular expressions to create
     * {@link RegexRequestMatcher} from
     * @return a {@link List} of {@link RegexRequestMatcher} instances
     */
    public static List<RequestMatcher> regexMatchers(String... regexPatterns) {
        return regexMatchers(null, regexPatterns);
    }

}
