package com.dt.module.dex.enums;

/**
 * 访问标识;
 *
 * @author yanping
 * @date 2021/10/29 6:22 下午
 */
public enum AccessFlagEnum {

    ACC_PUBLIC("ACC_PUBLIC", 0x00000001),
    ACC_PRIVATE("ACC_PRIVATE", 0x00000002),
    ACC_PROTECTED("ACC_PROTECTED", 0x00000004),
    ACC_STATIC("ACC_STATIC", 0x00000008),
    ACC_FINAL("ACC_FINAL", 0x00000010),
    ACC_SYNCHRONIZED("ACC_SYNCHRONIZED", 0x00000020),
    ACC_SUPER("ACC_SUPER", 0x00000020),
    ACC_VOLATILE("ACC_VOLATILE", 0x00000040),
    ACC_BRIDGE("ACC_BRIDGE", 0x00000040),
    ACC_TRANSIENT("ACC_TRANSIENT", 0x00000080),
    ACC_VARARGS("ACC_VARARGS", 0x00000080),
    ACC_NATIVE("ACC_NATIVE", 0x00000100),
    ACC_INTERFACE("ACC_INTERFACE", 0x00000200),
    ACC_ABSTRACT("ACC_ABSTRACT", 0x00000400),
    ACC_STRICT("ACC_STRICT", 0x00000800),
    ACC_SYNTHETIC("ACC_SYNTHETIC", 0x00001000),
    ACC_ANNOTATION("ACC_ANNOTATION", 0x00002000),
    ACC_ENUM("ACC_ENUM", 0x00004000),
    ACC_CONSTRUCTOR("ACC_CONSTRUCTOR", 0x00010000),
    ACC_DECLARED_SYNCHRONIZED("ACC_DECLARED_SYNCHRONIZED", 0x00020000),
    ACC_CLASS_MASK("ACC_CLASS_MASK", (ACC_PUBLIC.value | ACC_FINAL.value | ACC_INTERFACE.value | ACC_ABSTRACT.value
            | ACC_SYNTHETIC.value | ACC_ANNOTATION.value | ACC_ENUM.value)),
    ACC_INNER_CLASS_MASK("ACC_INNER_CLASS_MASK", (ACC_PUBLIC.value | ACC_FINAL.value | ACC_INTERFACE.value | ACC_ABSTRACT.value
            | ACC_SYNTHETIC.value | ACC_ANNOTATION.value | ACC_ENUM.value | ACC_PRIVATE.value | ACC_PROTECTED.value | ACC_STATIC.value)),
    ACC_FIELD_MASK("ACC_FIELD_MASK", (ACC_PUBLIC.value | ACC_PRIVATE.value | ACC_PROTECTED.value | ACC_STATIC.value | ACC_FINAL.value
            | ACC_VOLATILE.value | ACC_TRANSIENT.value | ACC_SYNTHETIC.value | ACC_ENUM.value)),
    ACC_METHOD_MASK("ACC_METHOD_MASK", (ACC_PUBLIC.value | ACC_PRIVATE.value | ACC_PROTECTED.value | ACC_STATIC.value | ACC_FINAL.value
            | ACC_SYNCHRONIZED.value | ACC_BRIDGE.value | ACC_VARARGS.value | ACC_NATIVE.value
            | ACC_ABSTRACT.value | ACC_STRICT.value | ACC_SYNTHETIC.value | ACC_CONSTRUCTOR.value
            | ACC_DECLARED_SYNCHRONIZED.value));


    AccessFlagEnum(String tag, int value) {
        this.name = tag;
        this.value = value;
    }

    private String name;
    private int value;

    public static String valueOfName(Integer value) {

        if (null == value) {
            return null;
        }

        for (AccessFlagEnum node : AccessFlagEnum.values()) {
            if (node.value == value) {
                return node.name;
            }
        }
        return String.valueOf(value);
    }
}

