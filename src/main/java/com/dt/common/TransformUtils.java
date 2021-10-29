package com.dt.common;

import com.dt.module.dex.entity.LebReadResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanping
 * @date 2021/10/22 7:05 下午
 */
@Slf4j
public class TransformUtils {

    /**
     * 转化为非转义字符;
     *
     * @param data
     * @return
     */
    public static String withNoEscape(String data) {
        try {
            String tmpData1 = data.replace("\n", "\\n");
            String tmpData = tmpData1.replace("\0", "\\0");
            return tmpData;
        } catch (Exception e) {
            log.error("Exception:[{}], {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * little endian
     * byte[] 转 int
     * this is for little endian
     */
    public static int bytes2Int(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return 0;
        return (bytes[3] & 0XFF) << 24
                | (bytes[2] & 0xFF) << 16
                | (bytes[1] & 0xFF) << 8
                | bytes[0] & 0xFF;
    }

    /**
     * byte[] 转 unsigned short
     */
    public static int bytes2UnsignedShort(byte[] bytes) {
        if (null == bytes || bytes.length == 0) return 0;
        return ((bytes[0] & 0xff) |
                ((bytes[1] & 0xff)) << 8);
    }

    /**
     * byte数组转unsigned int;
     *
     * @param bytes
     * @return
     */
    public static long bytes2UnsignedInt(byte[] bytes) {
        try {
            if (null == bytes || bytes.length == 0) return 0;
            return (long) (bytes[3] & 0XFF) << 24
                    | (bytes[2] & 0xFF) << 16
                    | (bytes[1] & 0xFF) << 8
                    | bytes[0] & 0xFF;
        } catch (Exception e) {
            log.error("Exception:[{}],{}", e.getMessage(), e);
        }
        return 0l;
    }

    /**
     * byte[] 转 16进制字符串
     */
    public static String byte2HexStr(byte[] bytes) {
        try {
            if (null == bytes || bytes.length == 0) return "";
            StringBuilder sb = new StringBuilder("0x");
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Exception:[{}],{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 反转字符串
     */
    public static byte[] reverseBytes(byte[] bytes) {
        int length = bytes.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length / 2; i++) {
            result[i] = bytes[length - i - 1];
            result[length - i - 1] = bytes[i];
        }
        return result;
    }

    public static byte[] copy(byte[] stringContent, int start, int length) {
        try {
            byte[] b = new byte[length];
            System.arraycopy(stringContent, start, b, 0, length);
            return b;
        } catch (Exception e) {
            log.error("input[{}][{}],Exception:[{}], {}", start, length, e.getMessage(), e);
        }
        return null;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    public static int byte2Int(byte[] bytes) {
        return fromBytes(bytes[3], bytes[2], bytes[1], bytes[0]);
    }

    public static int fromBytes(byte b1, byte b2, byte b3, byte b4) {
        return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
    }

    /**
     * 读取uleb128数据;
     * <p>
     * LEB128 类型。它是一种可变长度类型，每个 LEB128 由 1~5 个字节组成，每个字节只有 7 个有效位。
     * 如果第一个字节的最高位为 1，表示需要继续使用第 2 个字节，如果第二个字节最高位为 1，表示需要继续使用第三个字节，
     * 依此类推，直到最后一个字节的最高位为 0，至多 5 个字节。除了 LEB128 以外，还有无符号类型 ULEB128。
     *
     * @param src        原始数据数组;
     * @param offset     开始读取的位置;
     * @param readResult 读取的结果;
     * @return 读取的数据结果;
     */
    public static int readUnsignedLeb128(byte[] src, int offset, LebReadResult readResult) {

        int result = 0;
        int count = 0;
        int cur;
        try {

            do {
                cur = copy(src, offset, 1)[0];
                cur &= 0xff;
                result |= (cur & 0x7f) << count * 7;
                count++;
                offset++;
            } while ((cur & 0x80) == 128 && count < 5);

            if (null != readResult) {
                readResult.setReadCnt(count);
                readResult.setResult(result);
            }
        } catch (Exception e) {
            log.error("readUnsignedLeb128 Exception:[{}], {}", e.getMessage(), e);
        }
        return result;
    }

}
