package com.dt.module.dex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yanping
 * @date 2021/10/23 7:32 下午
 */
@Data
@AllArgsConstructor
public class DexFieldInfo {

    int classIdx;//字段所属的类
    int typeIdx;//字段的类型
    int nameIdx;//字段名

}
