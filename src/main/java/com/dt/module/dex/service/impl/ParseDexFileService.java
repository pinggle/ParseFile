package com.dt.module.dex.service.impl;

import cn.hutool.core.io.FileUtil;
import com.dt.module.dex.service.IParseDexFile;
import lombok.extern.slf4j.Slf4j;

/**
 * 解析Dex文件;
 *
 * @author yanping
 * @date 2021/10/22 4:32 下午
 */
@Slf4j
public class ParseDexFileService implements IParseDexFile {


    @Override
    public void parseDexFile(String filePath) {
        try {

            if (!FileUtil.exist(filePath)) {
                log.error("文件[{}]不存在", filePath);
                return;
            }

            byte[] fileByteArray = FileUtil.readBytes(filePath);
            log.info("read file :[{}]", fileByteArray);


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
