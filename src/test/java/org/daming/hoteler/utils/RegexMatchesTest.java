package org.daming.hoteler.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RegexMatchesTest {

    @Test
    void matches() {
        var urls = List.of("/api/v1/token", "/api/v1/customer",  "/api/v1/customer/1", "/dashboard");
        var pattern = "^/api/v\\d/(?!.*?token).*$";

        var r = Pattern.compile(pattern);
        assertFalse(r.matcher(urls.get(0)).matches());
        assertTrue(r.matcher(urls.get(1)).matches());
        assertTrue(r.matcher(urls.get(2)).matches());
        assertFalse(r.matcher(urls.get(3)).matches());
    }
}
