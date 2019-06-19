package com.xuxd.chat.common;

import com.xuxd.chat.common.exception.BootstarpException;
import com.xuxd.chat.common.netty.NettyConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.xuxd.chat.common.Constants.Server;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:52
 * @Description: 命令行参数解析器
 */
public class CmdParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmdParser.class);

    private CommandLine commandLine;

    private CmdParser(String[] args, String appName, Options options) {
        this.args = args;
        this.appName = appName;
        this.options = options;
        this.commandLine = parseCommandLine();
    }

    private String args[];
    private String appName;
    private Options options;

    public static CmdParserBuild create() {
        return new CmdParserBuild();
    }

    public CmdParser addOption(Option option) {
        this.options.addOption(option);
        return this;
    }

    public void cmd2Config(NettyConfig nettyConfig) {
        Map<String, String>  map = new HashMap<String, String>();
        Iterator<Option> iterator = commandLine.iterator();
        while (iterator.hasNext()) {
            Option option = iterator.next();
            map.put(option.getOpt(), option.getValue());
        }
        nettyConfig.setConfig(map);
    }

    public boolean validate() {
        if (this.options == null) {
            return true;
        }
        if (this.commandLine == null) {
            printHelp();
            return false;
        }
        List<String> argList = commandLine.getArgList();

        if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
            printHelp();
            return false;
        }
        for (String arg : argList) {
            if (!options.hasOption(arg)) {
                printHelp();
                return false;
            }
        }
        return true;
    }

    private void printHelp() {
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        hf.printHelp(appName, options, true);
    }

    private CommandLine parseCommandLine() {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(this.options, this.args);
        } catch (ParseException e) {
            if (e instanceof MissingArgumentException) {
                return null;
            } else {
                LOGGER.error("初始化参数解析失败", e);
                throw new BootstarpException("初始化参数解析失败", e);
            }
        }
    }

    public static class CmdParserBuild {

        private String args[];
        private String appName;
        private Options options = new Options();
        private boolean called;

        CmdParserBuild() {

        }


        public CmdParserBuild addOption(Option opt) {
            this.options.addOption(opt);
            return this;
        }

        public CmdParserBuild args(String[] args) {
            this.args = args;
            return this;
        }

        public CmdParserBuild appName(String appName) {
            this.appName = appName;
            return this;
        }

        public CmdParserBuild addHelp() {
            Option opt = new Option("h", "help", false, "打印帮助信息");
            opt.setRequired(false);
            options.addOption(opt);
            return this;
        }

        // 服务端配置
        public CmdParserBuild server() {
            if (called) {
                throw new UnsupportedOperationException("已经调用过server/client方法");
            }
            called = true;
            Option option = new Option(Server.HOST_S, Server.HOST_L, true, "服务器监听地址,e.g. 127.0.0.1, 默认：0.0.0.0");
            option.setRequired(false);
            this.options.addOption(option);

            option = new Option(Server.PORT_S, Server.PORT_L, true, "服务器监听端口，e.g. 8888，默认：8888");
            option.setRequired(false);
            this.options.addOption(option);
            return this;
        }

        // 客户端配置
        public CmdParserBuild client() {
            if (called) {
                throw new UnsupportedOperationException("已经调用过server/client方法");
            }
            Option option = new Option(Server.HOST_S, Server.HOST_L, true, "服务器地址,e.g. 127.0.0.1, 默认：0.0.0.0");
            option.setRequired(false);
            this.options.addOption(option);

            option = new Option(Server.PORT_S, Server.PORT_L, true, "服务器端口，e.g. 8888，默认：8888");
            option.setRequired(false);
            this.options.addOption(option);

            called = true;
            return this;
        }

        public CmdParser build() {
            return new CmdParser(args, appName, options);
        }

    }

}
