package com.dt.module.dex.enums;

/**
 * @author yanping
 * @date 2021/10/29 9:55 下午
 */
public enum MapItemTypeEnum {

    kDexTypeHeaderItem("kDexTypeHeaderItem", 0x0000),
    kDexTypeStringIdItem("kDexTypeStringIdItem", 0x0001),
    kDexTypeTypeIdItem("kDexTypeTypeIdItem", 0x0002),
    kDexTypeProtoIdItem("kDexTypeProtoIdItem", 0x0003),
    kDexTypeFieldIdItem("kDexTypeFieldIdItem", 0x0004),
    kDexTypeMethodIdItem("kDexTypeMethodIdItem", 0x0005),
    kDexTypeClassDefItem("kDexTypeClassDefItem", 0x0006),
    kDexTypeCallSiteIdItem("kDexTypeCallSiteIdItem", 0x0007),
    kDexTypeMethodHandleItem("kDexTypeMethodHandleItem", 0x0008),
    kDexTypeMapList("kDexTypeMapList", 0x1000),
    kDexTypeTypeList("kDexTypeTypeList", 0x1001),
    kDexTypeAnnotationSetRefList("kDexTypeAnnotationSetRefList", 0x1002),
    kDexTypeAnnotationSetItem("kDexTypeAnnotationSetItem", 0x1003),
    kDexTypeClassDataItem("kDexTypeClassDataItem", 0x2000),
    kDexTypeCodeItem("kDexTypeCodeItem", 0x2001),
    kDexTypeStringDataItem("kDexTypeStringDataItem", 0x2002),
    kDexTypeDebugInfoItem("kDexTypeDebugInfoItem", 0x2003),
    kDexTypeAnnotationItem("kDexTypeAnnotationItem", 0x2004),
    kDexTypeEncodedArrayItem("kDexTypeEncodedArrayItem", 0x2005),
    kDexTypeAnnotationsDirectoryItem("kDexTypeAnnotationsDirectoryItem", 0x2006);

    MapItemTypeEnum(String tag, int value) {
        this.name = tag;
        this.value = value;
    }

    private String name;
    private int value;

    public static String valueOfName(Integer value) {

        if (null == value) {
            return null;
        }

        for (MapItemTypeEnum node : MapItemTypeEnum.values()) {
            if (node.value == value) {
                return node.name;
            }
        }
        return String.valueOf(value);
    }
}
