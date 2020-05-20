package com.tzy.demo.seed.core;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzanyong
 * @description 通用对象实例转换
 * @date 2020/5/20
 **/
public class ModelDefaultTransfer {
    /**
     * 单对象复制转换
     *
     * @param s   源对象实例
     * @param cls 目标对象类
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T transfer(S s, Class<T> cls) {
        T target = ModelDefaultTransfer.getNewObject(cls);
        BeanUtils.copyProperties(s, target);
        return target;
    }

    /**
     * 单对象复制转换，可传入需要忽略复制的字段
     *
     * @param s                源对象实例
     * @param cls              目标对象类
     * @param ignoreProperties 需要忽略复制的字段
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T transfer(S s, Class<T> cls, String... ignoreProperties) {
        T target = ModelDefaultTransfer.getNewObject(cls);
        BeanUtils.copyProperties(s, target, ignoreProperties);
        return target;
    }

    /**
     * 对象列表复制转换
     *
     * @param list 源对象实例列表
     * @param cls  目标对象类
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> transferList(List<S> list, Class<T> cls) {
        List<T> results = new ArrayList<>();
        for (S s : list) {
            T target = ModelDefaultTransfer.getNewObject(cls);
            BeanUtils.copyProperties(s, target);
            results.add(target);
        }
        return results;
    }

    /**
     * 对象列表复制转换，可传入需要忽略复制的字段
     *
     * @param list             源对象实例列表
     * @param cls              目标对象类
     * @param ignoreProperties 需要忽略复制的字段
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> transferList(List<S> list, Class<T> cls, String... ignoreProperties) {
        List<T> results = new ArrayList<>();
        for (S s : list) {
            T target = ModelDefaultTransfer.getNewObject(cls);
            BeanUtils.copyProperties(s, target, ignoreProperties);
            results.add(target);
        }
        return results;
    }

    private static <T> T getNewObject(Class<T> cls) {
        T t = null;
        try {
            t = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
