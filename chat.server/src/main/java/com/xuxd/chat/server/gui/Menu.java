package com.xuxd.chat.server.gui;

import com.xuxd.chat.common.Constants;
import com.xuxd.chat.common.exception.BootstrapException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dong on 2019/6/20.
 * 菜单
 */
final public class Menu {
    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);
    private static final Menu INSTANCE = new Menu();
    private static String welcome;
    private static String menu;

    private Menu() {
    }

    static {
        INSTANCE.initialize();
    }

    public static Menu create() {
        return INSTANCE;
    }

    private void initialize() {
        LOGGER.info("initialize menu");
        welcome();

    }

    public String welcome() {
        if (welcome == null) {
            synchronized (this) {
                if (welcome == null) {
                    try {
                        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("welcome.txt")) {
                            byte[] bytes = new byte[inputStream.available()];
                            IOUtils.read(inputStream, bytes);
                            welcome = new String(bytes, Constants.CharsetName.UTF_8);
                        }
                    } catch (IOException e) {
                        throw new BootstrapException("文件读取失败", e);
                    }
                }
            }
        }

        return welcome;
    }

    public String menu() {
        menu = "";
        return menu;
    }
}
