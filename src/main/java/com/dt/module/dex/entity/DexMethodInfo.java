package com.dt.module.dex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yanping
 * @date 2021/10/23 7:32 下午
 */
@Data
@AllArgsConstructor
public class DexMethodInfo {

    int classIdx;//指向typeIds,表示方法所在类的类型;
    int typeIdx;//指向typeIds,表示方法声明的类型;
    int nameIdx;//指向stringIds,表示方法名称;

}
