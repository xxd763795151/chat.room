package com.xuxd.chat.server;

import com.xuxd.chat.common.CmdParser;
import com.xuxd.chat.common.utils.ConfigUtils;
import com.xuxd.chat.server.netty.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: 许晓东
 * @Date: 19-6-14 14:35
 * @Description: 服务端启动类
 */
public class ServerStartup {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStartup.class);
    private static CmdParser cmdParser;

    public static void main(String[] args) {
        LOGGER.info("start running chat server");
        try {
            createServer(args).start();
        } catch (Exception e) {
            LOGGER.error("Start failed: ", e);
        }
        LOGGER.info("chat server exit");
    }

    private static ChatServer createServer(String[] args) throws Exception {
        // 初始化命令行参数解析器
        initCmdParser(args);

        NettyServerConfig config = new NettyServerConfig();
        ConfigUtils.load(config);
        // 设置参数到服务器配置
        cmdParser.cmd2Config(config);
        ChatServer server = new ChatServer(config);
        return server;
    }

    private static void initCmdParser(String[] args) {

        cmdParser = CmdParser
                .create()
                .addHelp()
                .appName("server")
                .args(args)
                .server()
                .build();

        // 命令行参数选项是否合法
        boolean pass = cmdParser.validate();
        if (!pass) {
            System.exit(-1);
        }

    }


}
