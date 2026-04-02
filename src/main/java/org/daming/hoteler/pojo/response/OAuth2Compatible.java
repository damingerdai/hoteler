package org.daming.hoteler.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OAuth2 标准兼容接口
 */
public interface OAuth2Compatible {

    @Schema(description = "OAuth2 访问令牌", example = "eyJhbGci...")
    @JsonProperty("access_token")
    String getAccessToken();

    @Schema(description = "令牌类型", example = "bearer")
    @JsonProperty("token_type")
    default String getTokenType() {
        return "bearer";
    }

    @Schema(description = "有效时间(秒)", example = "3600")
    @JsonProperty("expires_in")
    default long getExpiresIn() {
        return 60L * 60L; // 默认一个小时，或者根据业务计算
    }
}
