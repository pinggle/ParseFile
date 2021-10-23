package com.dt.module.dex.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * 方法声明信息;
 *
 * @author yanping
 * @date 2021/10/23 3:34 下午
 */
@Data
public class DexProtoInfo {

    public static final int DexTypeItemUnitSize = 2;

    public DexProtoInfo(int shortIdIdx, int returnTypeIdx, int parametersOff) {
        this.shortIdIdx = shortIdIdx;
        this.returnTypeIdx = returnTypeIdx;
        this.parametersOff = parametersOff;
    }

    int shortIdIdx;//方法声明字符串指向DexStringId中的index;
    int returnTypeIdx;//方法返回类型,指向DexTypeId的index;
    int parametersOff;//方法参数列表的偏移地址;
    DexTypeList dexTypeList = new DexTypeList();//方法参数信息;

    String protoInfo;

    /**
     * 存放方法的参数列表;
     */
    @Data
    public static class DexTypeList {
        int size;//DexTypeItem的个数;
        ArrayList<DexTypeItem> dexTypeItems = new ArrayList<>();//DexTypeItem的数组;
    }

    @Data
    public static class DexTypeItem {
        int typeIdx;//指向DexTypeId列表的index; (为u2,short类型)
    }

}
