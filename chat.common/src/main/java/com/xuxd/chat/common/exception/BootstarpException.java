package com.xuxd.chat.common.exception;

/**
 * @Auther: 许晓东
 * @Date: 19-6-14 16:12
 * @Description: 启动异常
 */
public class BootstarpException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public BootstarpException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.throwable = throwable;
    }
}
