package com.tzy.demo.seed;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tangzanyong
 * @description 单元测试继承该类即可
 * @date 2020/5/20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeedApplication.class)
@Transactional
@Rollback
public abstract class Tester {}
