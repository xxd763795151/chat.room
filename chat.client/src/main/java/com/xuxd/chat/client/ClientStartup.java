package com.xuxd.chat.client;

import com.xuxd.chat.client.netty.NettyClientConfig;
import com.xuxd.chat.common.CmdParser;
import com.xuxd.chat.common.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dong on 2019/6/19.
 * 客户端启动
 */
public class ClientStartup {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientStartup.class);
    private static CmdParser cmdParser;

    public static void main(String[] args) {

        LOGGER.info("start running chat client");
        try {
            createClient(args).start();
        } catch (Exception e) {
            LOGGER.error("Start failed", e);
        }
        LOGGER.info("chat client exit");

    }

    private static ChatClient createClient(String[] args) throws Exception {
        initCmdParser(args);
        NettyClientConfig clientConfig = new NettyClientConfig();
        ConfigUtils.load(clientConfig);
        cmdParser.cmd2Config(clientConfig);
        ChatClient chatClient = new ChatClient(clientConfig);
        return chatClient;
    }

    private static void initCmdParser(String[] args) {

        cmdParser = CmdParser
                .create()
                .addHelp()
                .appName("client")
                .args(args)
                .client()
                .build();

        // 命令行参数选项是否合法
        boolean pass = cmdParser.validate();
        if (!pass) {
            System.exit(-1);
        }
    }
}