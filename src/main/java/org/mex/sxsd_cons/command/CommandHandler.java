package org.mex.sxsd_cons.command;

public interface CommandHandler {

    /**
     * 触发关键词
     */
    String trigger();

    /**
     * 命令描述
     */
    String description();

    /**
     * 命令实现
     * @param command 参数
     * @return 是否运行成功
     */
    boolean handleCommand(String[] command);
}
