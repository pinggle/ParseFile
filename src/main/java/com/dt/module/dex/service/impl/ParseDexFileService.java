package com.dt.module.dex.service.impl;

import cn.hutool.core.io.FileUtil;
import com.dt.common.TransformUtils;
import com.dt.module.dex.entity.DexFileInfo;
import com.dt.module.dex.entity.DexHeader;
import com.dt.module.dex.entity.DexStringInfo;
import com.dt.module.dex.service.IParseDexFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.RandomAccessFile;

import static com.dt.module.dex.entity.DexHeader.DEX_FIELD_FOUR_SIZE;

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
            log.info("read file :[{}]", fileByteArray);

            byte[] tmpFourBuffer = new byte[DEX_FIELD_FOUR_SIZE];
            byte[] tmpStringBuffer = new byte[BUFFER_MAX_SIZE];
            DexFileInfo dexFileInfo = new DexFileInfo();

            RandomAccessFile randomAccessDexFile = new RandomAccessFile(filePath, "r");

            InputStream dexFileInputStream = FileUtil.getInputStream(filePath);

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
                    //log.info("read: len->[{}],data->[{}]", stringInfoLen, stringInfoData);
                    dexStringInfo.setLen(stringInfoLen);
                    dexStringInfo.setData(stringInfoData);
                    readCount++;
                } else {
                    log.error("read: len->[{}],data->[{}]", stringInfoLen, stringInfoData);
                }
            }

            log.info("=================== Dex 实际读取字符串个数:[{}] ===================", readCount);

            log.info("=================== Dex 读取字符串 End ===================");


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
