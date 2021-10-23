package com.dt.module.dex.entity;

import lombok.Data;

/**
 * Dex文件头信息;
 *
 * @author yanping
 * @date 2021/10/22 6:10 下午
 */
@Data
public class DexHeader {

    public static final int DEX_FIELD_FOUR_SIZE = 4;
    public static final int DEX_HEADER_MAGIC_SIZE = 8;
    public static final int DEX_HEADER_SIGNATURE_SIZE = 20;

    private byte[] magic = new byte[DEX_HEADER_MAGIC_SIZE];//文件标识，一般为"dex\n035\0" => dex + 换行符 + dex版本 + 0
    private byte[] checksum = new byte[DEX_FIELD_FOUR_SIZE];//adler32校验用来确保Dex文件内容无损
    private byte[] signature = new byte[DEX_HEADER_SIGNATURE_SIZE];//签名信息(sha-1哈希)
    private byte[] fileSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//Dex文件大小
    private byte[] headerSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//Dex文件头大小，一般为70字节
    private byte[] endianTag = new byte[DEX_FIELD_FOUR_SIZE];
    ;//字节序，默认为0x12345678，Little Endian格式
    private byte[] linkSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//LinkSection(链接段)大小
    private byte[] linkOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//LinkSection(链接段)的偏移地址
    private byte[] mapOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexMapList偏移地址
    private byte[] stringIdsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexStringId(字符串列表)的个数
    private byte[] stringIdsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexStringId(字符串列表)的偏移地址
    private byte[] typeIdsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexTypeId(类型列表)的个数
    private byte[] typeIdsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexTypeId(类型列表)的偏移地址
    private byte[] protoIdsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexProtoId(声明列表)的个数
    private byte[] protoIdsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexProtoId(声明列表)的偏移地址
    private byte[] fieldIdsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexFieldId(字段列表)的个数
    private byte[] fieldIdsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexFieldId(字段列表)的偏移地址
    private byte[] methodIdsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexMethodId(方法列表)的个数
    private byte[] methodIdsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexMethodId(方法列表)的偏移地址
    private byte[] classDefsSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexClassDef(类列表)的个数
    private byte[] classDefsOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//DexClassDef(类列表)的偏移地址
    private byte[] dataSize = new byte[DEX_FIELD_FOUR_SIZE];
    ;//数据段的大小;
    private byte[] dataOff = new byte[DEX_FIELD_FOUR_SIZE];
    ;//数据段的偏移地址

//    private byte[8] magic;//文件标识，一般为"dex\n035\0" => dex + 换行符 + dex版本 + 0
//    private byte[4] checksum;//adler32校验用来确保Dex文件内容无损
//    private byte[20] signature;//签名信息(sha-1哈希)
//    private byte[4] fileSize;//Dex文件大小
//    private byte[4] headerSize;//Dex文件头大小，一般为70字节
//    private byte[4] endianTag;//字节序，默认为0x12345678，Little Endian格式
//    private byte[4] linkSize;//LinkSection(链接段)大小
//    private byte[4] linkOff;//LinkSection(链接段)的偏移地址
//    private byte[4] mapOff;//DexMapList偏移地址
//    private byte[4] stringIdsSize;//DexStringId(字符串列表)的个数
//    private byte[4] stringIdsOff;//DexStringId(字符串列表)的偏移地址
//    private byte[4] typeIdsSize;//DexTypeId(类型列表)的个数
//    private byte[4] typeIdsOff;//DexTypeId(类型列表)的偏移地址
//    private byte[4] protoIdsSize;//DexProtoId(声明列表)的个数
//    private byte[4] protoIdsOff;//DexProtoId(声明列表)的偏移地址
//    private byte[4] fieldIdsSize;//DexFieldId(字段列表)的个数
//    private byte[4] fieldIdsOff;//DexFieldId(字段列表)的偏移地址
//    private byte[4] methodIdsSize;//DexMethodId(方法列表)的个数
//    private byte[4] methodIdsOff;//DexMethodId(方法列表)的偏移地址
//    private byte[4] classDefsSize;//DexClassDef(类列表)的个数
//    private byte[4] classDefsOff;//DexClassDef(类列表)的偏移地址
//    private byte[4] dataSize;//数据段的大小;
//    private byte[4] dataOff;//数据段的偏移地址
}
