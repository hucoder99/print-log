package com.single.log.module;

import java.io.Serializable;

/**
 * DTO基类
 *
 * @author single
 * @date 2022/6/10
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "id=" + id +
                '}';
    }
}
