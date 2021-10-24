package com.dt.module.dex.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * 类的信息;
 *
 * @author yanping
 * @date 2021/10/23 8:04 下午
 */
@Data
@Accessors(chain = true)
public class ClassDefInfo {

    int classIdx;       /* 索引,指向typeIds,表示类信息; */
    int accessFlags;    /* 访问标识,如public; */
    int superClassIdx;  /* 索引,指向typeIds,标识父类信息; */
    int interfacesOff;  /* 指向DexTypeList的偏移地址,表示接口信息; */
    int sourceFileIdx;  /* 索引,指向stringIds,表示源文件名称; */
    int annotationsOff; /* 指向annotations_directory_item的偏移地址,表示注解信息; */
    int classDataOff;   /* 指向class_data_item的偏移地址,表示类的数据部分; */
    int staticValueOff; /* 指向DexEncodedArray的偏移地址,表示类的静态数据; */

    ParseInfo parseInfo = new ParseInfo();
    ClassDataItem classDataItem = new ClassDataItem();

    public ClassDefInfo(int classIdx, int accessFlags, int superClassIdx, int interfacesOff,
                        int sourceFileIdx, int annotationsOff, int classDataOff, int staticValueOff) {
        this.classIdx = classIdx;
        this.accessFlags = accessFlags;
        this.superClassIdx = superClassIdx;
        this.interfacesOff = interfacesOff;
        this.sourceFileIdx = sourceFileIdx;
        this.annotationsOff = annotationsOff;
        this.classDataOff = classDataOff;
        this.staticValueOff = staticValueOff;
    }

    @Data
    public static class ParseInfo {
        private String className;
    }

    @Data
    public static class ClassDataItem {
        int staticFieldSize;    /* 静态字段个数; */
        int instanceFieldSize;  /* 实例字段个数; */
        int directMethodsSize;  /* 直接方法个数; */
        int virtualMethodsSize; /* 虚方法个数; */

        DexField staticFields = new DexField();     /* 静态字段 */
        DexField instanceFields = new DexField();   /* 实例字段 */

        DexMethod directMethods = new DexMethod();  /* 直接方法 */
        DexMethod virtualMethods = new DexMethod(); /* 虚方法 */

    }

    @Data
    public static class DexField {
        int fieldIdx;   /* 索引,指向field_id_item */
        int accessFlag; /* 访问标识; */
    }

    @Data
    public static class DexMethod {
        int methodIdx;  /* 索引,指向method_id_item */
        int accessFlags;/* 访问标识; */
        int codeOff;    /* code_item的偏移地址 */
    }

    @Data
    public static class DexCode {
        int registerSize;   /* 寄存器个数 */
        int insSize;        /* 参数个数 */
        int outsSize;   /* 调用其他方法时使用的寄存器个数 */
        int triesSize;  /* try/cache语句个数 */
        int debugInfoOff;   /* debug信息的偏移地址 */
        int insnsSize;  /* 指令集的个数 */
        ArrayList insns = new ArrayList();  /* 指令集 */
    }


}
