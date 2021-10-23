package com.dt.module.dex.entity;

import lombok.Data;

/**
 * 类型信息;
 *
 * @author yanping
 * @date 2021/10/22 9:52 下午
 */
@Data
public class DexTypeInfo {

    public DexTypeInfo(int index) {
        this.index = index;
        this.len = 0;
        this.data = null;
    }

    /**
     * 在字符串中的索引值;
     **/
    private int index;

    /**
     * 字符串长度;
     */
    private int len;

    /**
     * 字符串内容;
     */
    private String data;

}
