# 解析文件
项目打包

./gradlew clean build shadowJar -x test

## 服务
- 解析Dex文件
  - 补充知识
    - LEB128 类型。它是一种可变长度类型，每个 LEB128 由 1~5 个字节组成，每个字节只有 7 个有效位。如果第一个字节的最高位为 1，表示需要继续使用第 2 个字节，如果第二个字节最高位为 1，表示需要继续使用第三个字节，依此类推，直到最后一个字节的最高位为 0，至多 5 个字节。除了 LEB128 以外，还有无符号类型 ULEB128。
  - 参考
  - https://source.android.google.cn/devices/tech/dalvik/dex-format
  - http://androidxref.com/9.0.0_r3/xref/dalvik/libdex/DexFile.h
  - https://juejin.cn/post/6844903847647772686
