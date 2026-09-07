package com.example.assessment.demos.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginInfoVO {
    private String username; // 用户名
    private String password; // 密码
    private String token; // 令牌
}
