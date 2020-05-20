package com.tzy.demo.seed.service.impl;

import com.tzy.demo.seed.dao.SysUserMapper;
import com.tzy.demo.seed.model.SysUser;
import com.tzy.demo.seed.service.SysUserService;
import com.tzy.demo.seed.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by tangzanyong on 2020/05/20.
 */
@Service
@Transactional
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

}
