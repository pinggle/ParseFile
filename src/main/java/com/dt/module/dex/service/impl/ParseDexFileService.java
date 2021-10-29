package com.dt.module.dex.service.impl;

import cn.hutool.core.io.FileUtil;
import com.dt.common.TransformUtils;
import com.dt.module.dex.entity.*;
import com.dt.module.dex.enums.AccessFlagEnum;
import com.dt.module.dex.service.IParseDexFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

import static com.dt.module.dex.entity.DexHeader.DEX_FIELD_FOUR_SIZE;
import static com.dt.module.dex.entity.DexProtoInfo.DexTypeItemUnitSize;

/**
 * 解析Dex文件;
 *
 * @author yanping
 * @date 2021/10/22 4:32 下午
 */
@Slf4j
public class ParseDexFileService implements IParseDexFile {


    private static final String DEX_MAGIC = "dex\n";
    private static final Integer BUFFER_MAX_SIZE = 4096;

    @Override
    public void parseDexFile(String filePath) {
        try {

            if (!FileUtil.exist(filePath)) {
                log.error("文件[{}]不存在", filePath);
                return;
            }

            byte[] fileByteArray = FileUtil.readBytes(filePath);
//            log.info("read file :[{}]", fileByteArray);

            byte[] tmpFourBuffer = new byte[DEX_FIELD_FOUR_SIZE];
            DexFileInfo dexFileInfo = new DexFileInfo();


            InputStream dexFileInputStream = FileUtil.getInputStream(filePath);

            ///////////////////////// 读取Dex头部信息 /////////////////////////
            log.info("=================== Dex Header Start ===================");
            dexFileInputStream.read(dexFileInfo.getDexHeader().getMagic(), 0, DexHeader.DEX_HEADER_MAGIC_SIZE);
            log.info("Magic(Dex文件标识):[{}]", TransformUtils.withNoEscape(new String(dexFileInfo.getDexHeader().getMagic())));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getChecksum(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("Checksum(Adler32校验值):[{}]", TransformUtils.bytes2UnsignedInt(dexFileInfo.getDexHeader().getChecksum()));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getSignature(), 0, DexHeader.DEX_HEADER_SIGNATURE_SIZE);
            log.info("Signature(SHA-1哈希值):[{}]", TransformUtils.byte2HexStr(dexFileInfo.getDexHeader().getSignature()));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getFileSize(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("FileSize(文件总大小):[{}]", TransformUtils.bytes2UnsignedInt(dexFileInfo.getDexHeader().getFileSize()));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getHeaderSize(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("DexHeader大小:[{}]", String.format("%d", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getHeaderSize())));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getEndianTag(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("字节序:[{}]", String.format("%x", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getEndianTag())));
            dexFileInputStream.read(dexFileInfo.getDexHeader().getLinkSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getLinkOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("linkSection:offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getLinkOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getLinkSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getMapOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("MapItem:offset:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getMapOff()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getStringIdsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getStringIdsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("stringId(字符串列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getStringIdsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getStringIdsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getTypeIdsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getTypeIdsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("typeId(类型列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getTypeIdsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getTypeIdsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getProtoIdsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getProtoIdsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("protoId(声明列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getProtoIdsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getProtoIdsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getFieldIdsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getFieldIdsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("fieldId(字段列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getFieldIdsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getFieldIdsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getMethodIdsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getMethodIdsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("methodId(方法列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getMethodIdsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getMethodIdsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getClassDefsSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getClassDefsOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("classDef(类列表):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getClassDefsOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getClassDefsSize()));

            dexFileInputStream.read(dexFileInfo.getDexHeader().getDataSize(), 0, DEX_FIELD_FOUR_SIZE);
            dexFileInputStream.read(dexFileInfo.getDexHeader().getDataOff(), 0, DEX_FIELD_FOUR_SIZE);
            log.info("dataSection(数据段):offset:[{}],size:[{}]", TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getDataOff()),
                    TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getDataSize()));
            log.info("=================== Dex Header End ===================");


            ///////////////////////// 读取Dex字符串信息 /////////////////////////
            log.info("=================== Dex 读取字符串 Start ===================");
            int dexStringInfoTotal = TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getStringIdsSize());
            for (int i = 0; i < dexStringInfoTotal; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                int stringInfoOffset = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInfo.getDexStringInfos().add(new DexStringInfo(stringInfoOffset));
            }
            log.info("=================== Dex 计划读取字符串个数:[{}] ===================", dexFileInfo.getDexStringInfos().size());
            /**
             * 开始遍历读取字符串,根据偏移,读取1字节长度出来,然后继续读取指定长度的字符串;
             */
            int readCount = 0;
            for (DexStringInfo dexStringInfo : dexFileInfo.getDexStringInfos()) {
                int stringInfoOffset = dexStringInfo.getOffset();
                byte size = fileByteArray[stringInfoOffset]; // 第一个字节表示该字符串的长度，之后是字符串内容
                int stringInfoLen = TransformUtils.byteToInt(size);
                String stringInfoData = new String(TransformUtils.copy(fileByteArray, stringInfoOffset + 1, stringInfoLen));
                if (StringUtils.isNotEmpty(stringInfoData)) {
                    // 打印的信息太多,先注释;
                    //log.info("read: len->[{}],data->[{}]", stringInfoLen, stringInfoData);
                    dexStringInfo.setLen(stringInfoLen);
                    dexStringInfo.setData(stringInfoData);
                    readCount++;
                } else {
                    log.error("read: len->[{}],data->[{}]", stringInfoLen, stringInfoData);
                }
            }
            log.info("=================== Dex  ===================", readCount);
            log.info("=================== Dex 读取字符串 End ===================");


            ///////////////////////// 读取Dex类型信息 /////////////////////////
            log.info("=================== Dex 读取类型信息 Start ===================");
            int dexTypeInfoTotal = TransformUtils.bytes2Int(dexFileInfo.getDexHeader().getTypeIdsSize());
            int realReadTypeInfoCount = 0;
            for (int i = 0; i < dexTypeInfoTotal; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                int typeInfoIndex = TransformUtils.bytes2Int(tmpFourBuffer);
                DexTypeInfo tmpTypeInfoNode = new DexTypeInfo(typeInfoIndex);
                if (typeInfoIndex < dexFileInfo.getDexStringInfos().size()) {
                    // 根据读取的数据,作为字符串池的索引,提取数据即可。
                    tmpTypeInfoNode.setLen(dexFileInfo.getDexStringInfos().get(typeInfoIndex).getLen());
                    tmpTypeInfoNode.setData(dexFileInfo.getDexStringInfos().get(typeInfoIndex).getData());
                    // 打印的信息太多,先注释;
//                    log.info("readTypeInfo[{}]: index:[{}],len:[{}],data:[{}]", i, typeInfoIndex,
//                            tmpTypeInfoNode.getLen(), tmpTypeInfoNode.getData());
                    dexFileInfo.getDexTypeInfos().add(tmpTypeInfoNode);
                    realReadTypeInfoCount++;
                } else {
                    log.error("readTypeInfo out range: index:[{}]", typeInfoIndex);
                }
            }
            log.info("======= Dex 计划读取类型个数:[{}],实际读取类型个数:[{}] =======", dexTypeInfoTotal, realReadTypeInfoCount);
            log.info("=================== Dex 读取类型信息 End ===================");


            ///////////////////////// 读取Dex方法声明信息 /////////////////////////
            log.info("=================== Dex 读取方法声明信息 Start ===================");
            int dexProtoInfoSize = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getProtoIdsSize());
            int dexProtoInfoOff = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getProtoIdsOff());
            for (int i = 0; i < dexProtoInfoSize; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                //方法声明字符串指向DexStringId中的index;
                int shortIdIdx = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                //方法返回类型,指向DexTypeId的index;
                int returnTypeIdx = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                //方法参数列表的偏移地址;
                int parametersOff = TransformUtils.bytes2Int(tmpFourBuffer);
                DexProtoInfo tmpProtoInfoNode = new DexProtoInfo(shortIdIdx, returnTypeIdx, parametersOff);

                log.info("proto-000:[{}],[{}],[{}]", shortIdIdx, returnTypeIdx, parametersOff);
//                shortIdIdx = TransformUtils.bytes2Int(TransformUtils.copy(fileByteArray, dexProtoInfoOff + i*12, 4));
//                returnTypeIdx = TransformUtils.bytes2Int(TransformUtils.copy(fileByteArray, dexProtoInfoOff + i*12 +4, 4));
//                parametersOff = TransformUtils.bytes2Int(TransformUtils.copy(fileByteArray, dexProtoInfoOff + i*12 +4+4, 4));
//                log.info("proto-001:[{}],[{}],[{}]", shortIdIdx,returnTypeIdx,parametersOff);

                log.info("protoInfo:desc:[{}],returnType:[{}]", dexFileInfo.getDexStringInfos().get(shortIdIdx).getData(),
                        dexFileInfo.getDexTypeInfos().get(returnTypeIdx).getData());

                StringBuilder tmpProtoInfo = new StringBuilder();
                tmpProtoInfo.append(String.format("[描述:%s;", dexFileInfo.getDexStringInfos().get(shortIdIdx).getData()));
                tmpProtoInfo.append(String.format("返回:%s;", dexFileInfo.getDexTypeInfos().get(returnTypeIdx).getData()));
                tmpProtoInfo.append("参数:(");
                if (0 != parametersOff) {
                    // 读取方法参数信息;
                    int dexTypeItemSize = TransformUtils.bytes2Int(TransformUtils.copy(fileByteArray, parametersOff, 4));
                    log.info("read dex type info with size:[{}]", dexTypeItemSize);

                    tmpProtoInfoNode.getDexTypeList().setSize(dexTypeItemSize);
                    if (dexTypeItemSize > 0) {
                        for (int j = 0; j < dexTypeItemSize; j++) {
                            int typeIdx = TransformUtils.bytes2UnsignedShort(TransformUtils.copy(fileByteArray, parametersOff + 4 + j * DexTypeItemUnitSize, 2));
                            DexProtoInfo.DexTypeItem dexTypeItemNode = new DexProtoInfo.DexTypeItem();
                            dexTypeItemNode.setTypeIdx(typeIdx);
                            if (typeIdx < dexFileInfo.getDexTypeInfos().size()) {
                                tmpProtoInfo.append(dexFileInfo.getDexTypeInfos().get(typeIdx).getData() + ",");
                                log.info("read proto 方法参数列表[{}]:[{}] => typeInfoIdx:[{}]", j, dexFileInfo.getDexTypeInfos().get(typeIdx).getData(), typeIdx);
                            } else {
                                log.error("read proto error with index [{}] out of limit size [{}]", typeIdx, dexFileInfo.getDexTypeInfos().size());
                            }
                            tmpProtoInfoNode.getDexTypeList().getDexTypeItems().add(dexTypeItemNode);
                        }
                    }

                } else {
                    log.info("protoInfo-void-func:desc:[{}],returnType:[{}]", dexFileInfo.getDexStringInfos().get(shortIdIdx).getData(),
                            dexFileInfo.getDexTypeInfos().get(returnTypeIdx).getData());
                }

                tmpProtoInfo.append(")]");
                tmpProtoInfoNode.setProtoInfo(tmpProtoInfo.toString());
                // 添加节点到ProtoInfo数组;
                if (null != tmpProtoInfoNode) {
                    dexFileInfo.getDexProtoInfos().add(tmpProtoInfoNode);
                }

            }
            log.info("=================== Dex 读取方法声明信息 End ===================");

            ///////////////////////// 读取Dex字段信息 /////////////////////////
            log.info("=================== Dex 读取字段信息 Start ===================");
            int dexFieldIdSize = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getFieldIdsSize());
            for (int i = 0; i < dexFieldIdSize; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, 2);
                //字段所属类,指向typeIds中的index;
                int classIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, 2);
                //字段的类型,指向typeIds中的index;
                int typeIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                //字段的名称,指向stringIds中的index;
                int nameIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);

                DexFieldInfo fieldInfo = new DexFieldInfo(classIdx, typeIdx, nameIdx);
                log.info("字段信息:[{}],[{}],[{}]", dexFileInfo.getDexTypeInfos().get(classIdx).getData(),
                        dexFileInfo.getDexTypeInfos().get(typeIdx).getData(),
                        dexFileInfo.getDexStringInfos().get(nameIdx).getData());
                dexFileInfo.getFieldIds().add(fieldInfo);

            }
            log.info("=================== Dex 读取字段信息 End ===================");

            ///////////////////////// 读取Dex方法信息 /////////////////////////
            log.info("=================== Dex 读取方法信息 Start ===================");
            int dexMethodIdSize = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getMethodIdsSize());
            for (int i = 0; i < dexMethodIdSize; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, 2);
                //方法所属类,指向typeIds中的index;
                int classIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, 2);
                //方法声明的类型,指向protoIds中的index;
                int protoIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                //方法的名称,指向stringIds中的index;
                int nameIdx = TransformUtils.bytes2UnsignedShort(tmpFourBuffer);

                DexMethodInfo methodInfo = new DexMethodInfo(classIdx, protoIdx, nameIdx);
                log.info("方法信息=>类:[{}],类型:[{}],名称:[{}]", dexFileInfo.getDexTypeInfos().get(classIdx).getData(),
                        dexFileInfo.getDexProtoInfos().get(protoIdx).getProtoInfo(),
                        dexFileInfo.getDexStringInfos().get(nameIdx).getData());
                dexFileInfo.getMethodIds().add(methodInfo);
            }
            log.info("=================== Dex 读取方法信息 End ===================");


            ///////////////////////// 读取Dex类的所有信息 /////////////////////////
            int dexClassDefsSize = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getClassDefsSize());
            int dexClassDefsOff = TransformUtils.byte2Int(dexFileInfo.getDexHeader().getClassDefsOff());
            log.info("=================== Dex 读取类的所有信息 Start [{}][{}] ===================",
                    dexClassDefsOff, dexClassDefsSize);
            for (int i = 0; i < dexClassDefsSize; i++) {
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 索引,指向typeIds,表示类信息;
                int classIdx = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 访问标识,如public;
                int accessFlags = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 索引,指向typeIds,标识父类信息;
                int superclassIdx = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 指向DexTypeList的偏移地址,表示接口信息;
                int interfacesOff = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 索引,指向stringIds,表示源文件名称;
                int sourceFileIdx = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 指向annotations_directory_item的偏移地址,表示注解信息;
                int annotationsOff = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 指向class_data_item的偏移地址,表示类的数据部分;
                int classDataOff = TransformUtils.bytes2Int(tmpFourBuffer);
                dexFileInputStream.read(tmpFourBuffer, 0, DEX_FIELD_FOUR_SIZE);
                // 指向DexEncodedArray的偏移地址,表示类的静态数据;
                int staticValueOff = TransformUtils.bytes2Int(tmpFourBuffer);
                // 新建结构体,加入到dex文件信息;
                ClassDefInfo classDefInfoNode = new ClassDefInfo(classIdx, accessFlags, superclassIdx,
                        interfacesOff, sourceFileIdx, annotationsOff, classDataOff, staticValueOff);
                classDefInfoNode.getParseInfo().setClassName(dexFileInfo.getDexStringInfos().get(sourceFileIdx).getData());
                dexFileInfo.getClassDefs().add(classDefInfoNode);
                log.info("类信息=>类:[{}]", classDefInfoNode.getParseInfo().getClassName());
//                // 解析接口信息;
//                int interfacesIdx = TransformUtils.bytes2Int(TransformUtils.copy(fileByteArray, interfacesOff, 4));
//                log.info("类信息=>接口数量:[{}]", interfacesIdx);
//                if(interfacesIdx < dexFileInfo.getDexProtoInfos().size()) {
//                    log.info("类信息=>接口信息:[{}]", dexFileInfo.getDexProtoInfos().get(interfacesIdx).getProtoInfo());
//                } else {
//                    log.info("类信息=>获取接口信息出错");
//                }
                // 解析类的信息;
                log.info("类信息的偏移地址:[{}]", classDataOff);
                if (0 == classDataOff) {
                    continue;
                }


                // 读取DexClassDataHead;
                int readOffset = classDataOff;
                LebReadResult readResult = new LebReadResult();
                TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                log.info("静态字段个数:[{}],读取字节数:[{}]", readResult.getResult(), readResult.getReadCnt());
                classDefInfoNode.getClassDataItem().setStaticFieldSize(readResult.getResult());
                readOffset += readResult.getReadCnt();

                TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                log.info("实例字段个数:[{}],读取字节数:[{}]", readResult.getResult(), readResult.getReadCnt());
                classDefInfoNode.getClassDataItem().setInstanceFieldSize(readResult.getResult());
                readOffset += readResult.getReadCnt();

                TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                log.info("直接方法个数:[{}],读取字节数:[{}]", readResult.getResult(), readResult.getReadCnt());
                classDefInfoNode.getClassDataItem().setDirectMethodsSize(readResult.getResult());
                readOffset += readResult.getReadCnt();

                TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                log.info("虚方法个数:[{}],读取字节数:[{}]", readResult.getResult(), readResult.getReadCnt());
                classDefInfoNode.getClassDataItem().setVirtualMethodsSize(readResult.getResult());
                readOffset += readResult.getReadCnt();

                // 读取静态字段数据;=>ClassDefInfo.DexField
                int staticFieldsSize = classDefInfoNode.getClassDataItem().getStaticFieldSize();
                for (int j = 0; j < staticFieldsSize; j++) {
                    ClassDefInfo.DexField staticFieldInfo = new ClassDefInfo.DexField();
                    TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                    if (readResult.getResult() < dexFileInfo.getFieldIds().size()) {
                        staticFieldInfo.setFieldIdx(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        staticFieldInfo.setAccessFlag(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        log.info("静态字段[{}]:fieldId:[{}],访问标识:[{}]", j,
                                dexFileInfo.getFieldIds().get(staticFieldInfo.getFieldIdx()),
                                staticFieldInfo.getAccessFlag());
                    } else {
                        log.error("读取静态字段 error with offset [{}], exit with [{}] < [{}]", i,
                                readResult.getResult(),
                                dexFileInfo.getFieldIds().size());
                        System.exit(0);
                    }
                }

                // 读取实例字段数据;=>ClassDefInfo.DexField
                int instanceFieldsSize = classDefInfoNode.getClassDataItem().getInstanceFieldSize();
                for (int j = 0; j < instanceFieldsSize; j++) {
                    ClassDefInfo.DexField staticFieldInfo = new ClassDefInfo.DexField();
                    TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                    if (readResult.getResult() < dexFileInfo.getFieldIds().size()) {
                        staticFieldInfo.setFieldIdx(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        staticFieldInfo.setAccessFlag(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        log.info("实例字段[{}]:fieldId:[{}],访问标识:[{}]", j,
                                dexFileInfo.getFieldIds().get(staticFieldInfo.getFieldIdx()),
                                staticFieldInfo.getAccessFlag());
                    } else {
                        log.error("读取实例字段 error with offset [{}], exit with [{}] < [{}]", i,
                                readResult.getResult(),
                                dexFileInfo.getFieldIds().size());
                        System.exit(0);
                    }
                }

                // 读取直接方法数据;=>ClassDefInfo.DexMethod
                int directMethodsSize = classDefInfoNode.getClassDataItem().getDirectMethodsSize();
                for (int j = 0; j < directMethodsSize; j++) {
                    ClassDefInfo.DexMethod directMethodInfo = new ClassDefInfo.DexMethod();
                    TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                    if (readResult.getResult() < dexFileInfo.getMethodIds().size()) {
                        directMethodInfo.setMethodIdx(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        directMethodInfo.setAccessFlags(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        directMethodInfo.setCodeOff(readResult.getResult());
                        readOffset += readResult.getReadCnt();

                        int methodIdx = directMethodInfo.getMethodIdx();
                        int methodNameIdx = dexFileInfo.getMethodIds().get(methodIdx).getNameIdx();
                        int methodClassIdx = dexFileInfo.getMethodIds().get(methodIdx).getClassIdx();
                        String methodName = dexFileInfo.getDexStringInfos().get(methodNameIdx).getData();

                        log.info("直接方法信息:idx:[{}]:methodId:[{}],访问标识:[{}],codeOff:[{}]", j,
                                methodName,
                                AccessFlagEnum.valueOfName(directMethodInfo.getAccessFlags()),
                                directMethodInfo.getCodeOff());
                    } else {
                        log.error("读取直接方法信息 error with offset [{}], exit with [{}] < [{}]", i,
                                readResult.getResult(),
                                dexFileInfo.getMethodIds().size());
                        System.exit(0);
                    }
                }

                // 读取虚方法数据;=>ClassDefInfo.DexMethod
                int virtualMethodsSize = classDefInfoNode.getClassDataItem().getVirtualMethodsSize();
                for (int j = 0; j < virtualMethodsSize; j++) {
                    ClassDefInfo.DexMethod virtualMethodInfo = new ClassDefInfo.DexMethod();
                    TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                    if (readResult.getResult() < dexFileInfo.getMethodIds().size()) {
                        virtualMethodInfo.setMethodIdx(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        virtualMethodInfo.setAccessFlags(readResult.getResult());
                        readOffset += readResult.getReadCnt();
                        TransformUtils.readUnsignedLeb128(fileByteArray, readOffset, readResult);
                        virtualMethodInfo.setCodeOff(readResult.getResult());
                        readOffset += readResult.getReadCnt();

                        int methodIdx = virtualMethodInfo.getMethodIdx();
                        int methodNameIdx = dexFileInfo.getMethodIds().get(methodIdx).getNameIdx();
                        int methodClassIdx = dexFileInfo.getMethodIds().get(methodIdx).getClassIdx();
                        String methodName = dexFileInfo.getDexStringInfos().get(methodNameIdx).getData();

                        log.info("虚方法信息:idx:[{}]:methodName:[{}],访问标识:[{}],codeOff:[{}]", j,
                                methodName,
                                AccessFlagEnum.valueOfName(virtualMethodInfo.getAccessFlags()),
                                virtualMethodInfo.getCodeOff());
                    } else {
                        log.error("读取虚方法信息 error with offset [{}], exit with [{}] < [{}]", i,
                                readResult.getResult(),
                                dexFileInfo.getMethodIds().size());
                        System.exit(0);
                    }
                }


            }
            log.info("=================== Dex 读取类的所有信息 End ===================");

        } catch (Exception e) {
            log.error("Exception: [{}], {}", e.getMessage(), e);
        }
    }


    ////////////////////////////////////////////////////////


    private void init() {
    }

    // 采用静态内部类,作为单例调用;
    private ParseDexFileService() {
        init();
    }

    public static final ParseDexFileService getInstance() {
        return ParseDexFileService.LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final ParseDexFileService LAZY = new ParseDexFileService();
    }
}
