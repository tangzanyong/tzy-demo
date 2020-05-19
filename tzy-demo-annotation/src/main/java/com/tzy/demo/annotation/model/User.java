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
public class User {
    private String id;
    private String username;
    private String password;
    private String name;
    private String tenantId;
    private String orgId;
    private String client_id;
    private String jti;
    private Long exp;
    private String loginIp;
    private String loginTime;
}
