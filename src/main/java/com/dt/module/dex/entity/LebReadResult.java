package com.dt.module.dex.entity;

import lombok.Data;

/**
 * 用于传递uleb128读取信息;
 *
 * @author yanping
 * @date 2021/10/29 4:21 下午
 */
@Data
public class LebReadResult {
    /**
     * 读取的结果;
     */
    int result;

    /**
     * 读取的字节数;
     */
    int readCnt;
}
