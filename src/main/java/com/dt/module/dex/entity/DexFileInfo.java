package com.dt.module.dex.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Dex文件信息;
 *
 * @author yanping
 * @date 2021/10/22 6:06 下午
 */
@Data
public class DexFileInfo {

    public DexHeader dexHeader = new DexHeader();

    public ArrayList<DexStringInfo> dexStringInfos = new ArrayList<>();
    public ArrayList<DexTypeInfo> dexTypeInfos = new ArrayList<>();
    public ArrayList<DexProtoInfo> dexProtoInfos = new ArrayList<>();
    public ArrayList<DexFieldInfo> fieldIds = new ArrayList<>();
    public ArrayList<DexMethodInfo> methodIds = new ArrayList<>();

}
