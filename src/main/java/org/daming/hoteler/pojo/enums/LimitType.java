package org.daming.hoteler.pojo.enums;

/**
 * @author daming
 * @version 2023-03-18 09:44
 **/
public enum LimitType {

    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求者IP进行限流
     */
    IP
}
