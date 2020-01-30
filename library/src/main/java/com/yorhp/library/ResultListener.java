package com.yorhp.library;

/**
 * @author Tyhj
 * @date 2020-01-31
 * @Description: java类作用描述
 */

public interface ResultListener<T> {
    /**
     * 数据返回
     *
     * @param t
     */
    void resultBack(T t);
}
