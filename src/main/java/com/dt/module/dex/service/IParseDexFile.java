package com.dt.module.dex.service;

/**
 * @author yanping
 * @date 2021/10/22 4:31 下午
 */
public interface IParseDexFile {

    /**
     * 解析指定的dex文件;
     *
     * @param filePath
     */
    void parseDexFile(String filePath);

}
