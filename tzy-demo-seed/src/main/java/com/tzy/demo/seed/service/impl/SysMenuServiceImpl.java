package com.tzy.demo.seed.service.impl;

import com.tzy.demo.seed.dao.SysMenuMapper;
import com.tzy.demo.seed.model.SysMenu;
import com.tzy.demo.seed.service.SysMenuService;
import com.tzy.demo.seed.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by tangzanyong on 2020/05/20.
 */
@Service
@Transactional
public class SysMenuServiceImpl extends AbstractService<SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

}
