package com.dt.module.dex.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * MapItem信息
 *
 * @author yanping
 * @date 2021/10/29 9:37 下午
 */
@Data
public class DexMapItemInfo {
    int size;

    ArrayList<MapItem> mapItemList = new ArrayList<>();

    @Data
    public static class MapItem {
        int type;   // 项的类型;
        int unused; // 未使用;
        long size;   // 在指定偏移量处找到的项数量;
        long offset; // 从文件开头到相关项的偏移量;
    }
}
