package com.tzy.demo.annotation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    String accessToken;
    String refreshToken;
    String jwtToken;
}
