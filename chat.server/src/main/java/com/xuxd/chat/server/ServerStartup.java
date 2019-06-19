package com.xuxd.chat.server;

import com.xuxd.chat.common.CmdParser;
import com.xuxd.chat.server.netty.NettyServer;
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
        LOGGER.info("开始启动服务器");
        createServer(args).start();
        LOGGER.info("服务器启动完成");
    }

    private static ChatServer createServer(String[] args) {
        initCmdParser(args);

        NettyServerConfig config = new NettyServerConfig();
        cmdParser.cmd2Config(config);
        NettyServer nettyServer = new NettyServer(config);
        ChatServer server = new ChatServer(nettyServer);
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
