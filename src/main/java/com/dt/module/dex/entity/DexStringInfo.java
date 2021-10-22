package com.dt.module.dex.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanping
 * @date 2021/10/22 8:16 下午
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DexStringInfo {

    public DexStringInfo(int offset) {
        this.offset = offset;
        this.len = 0;
        this.data = null;
    }

    /**
     * 偏移地址
     **/
    private int offset;

    /**
     * 字符串长度;
     */
    private int len;

    /**
     * 字符串内容;
     */
    private String data;

}
