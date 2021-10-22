package com.dt.config;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class TestArgs {

    @Parameter(names = "-run", description = "run mode", order = 1)
    public String runMode = "print";

    @Parameter(names = "-path", description = "the file contians key=>value ", order = 7)
    public String path = null;

    @Parameter(names = "--help", help = true, order = 9)
    public boolean help;

    @Parameter(names = "-v", description = "", order = 10)
    public boolean version = false;

    @Parameter(names = "-fileName", description = "file name", order = 13)
    public String fileName = "";

    @Parameter(names = "-type", description = "type", order = 15)
    public String type = "android";


}
