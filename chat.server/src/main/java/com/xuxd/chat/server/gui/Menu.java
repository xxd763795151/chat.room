package com.xuxd.chat.server.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dong on 2019/6/20.
 * 菜单
 */
final public class Menu {
    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);
    private static final Menu INSTANCE = new Menu();

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

    }

    public String welcome() {
        return "hello, world";
    }
}
