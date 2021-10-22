package com.dt;

import com.beust.jcommander.JCommander;
import com.dt.config.DTModel;
import com.dt.config.TestArgs;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanping
 * @date 2021/10/22 4:15 下午
 */
@Slf4j
public class ParseFileEntry {

    private static final String TAG = "DTPrint";

    public static void main(String[] args) {
        TestArgs testArgs = new TestArgs();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(testArgs)
                .build();

        jCommander.parse(args);

        if (testArgs.isHelp()) {
            jCommander.usage();
            return;
        }

        try {
            if (testArgs.getRunMode().equalsIgnoreCase(DTModel.MODE_PRINT)) {
                log.info(TAG, "start parse dex file");


            } else {
                log.info(TAG, "unknown command : [{}]", testArgs.getRunMode());
            }
        } catch (Exception e) {
            log.error("run error with {}", e);
        }
    }


}
