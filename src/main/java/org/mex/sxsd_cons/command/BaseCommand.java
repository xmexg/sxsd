package org.mex.sxsd_cons.command;

import org.mex.sxsd_cons.Console;
import org.mex.sxsd_cons.Init;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.user.BaseUser;

public class BaseCommand {

    public BaseCommand() {
        // 注册命令
        Console.command.register(new versionCommand());
        Console.command.register(new helpCommand());
        Console.command.register(new exitCommand());
        Console.command.register(new reloadLocUserCommand());
        Console.command.register(new saveLocUserCommand());
    }
}

// 帮助命令
class helpCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        //遍历所有命令
        Console.command.handlerMap.forEach((k, v) -> {
            PrintFormat.println(" - " + k + " : " + v.description(), PrintFormat.OUT);
        });
        return true;
    }
    @Override
    public String description() {
        return "帮助";
    }
    @Override
    public String trigger() {
        return "help";
    }
}

// 退出指令
class exitCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        System.exit(0);
        return true;
    }
    @Override
    public String description() {
        return "退出";
    }
    @Override
    public String trigger() {
        return "exit";
    }
}

// 输出版本
class versionCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        PrintFormat.println(" % 软件版本: " + Init.VERSION, PrintFormat.OUT);
        System.getProperties().forEach((k, v) -> {
            PrintFormat.println(" % " + k + " : " + v, PrintFormat.OUT);
        });

        return true;
    }
    @Override
    public String description() {
        return "输出版本";
    }
    @Override
    public String trigger() {
        return "version";
    }
}


// 重新加载本地用户列表
class reloadLocUserCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        return Init.UpBaseUserList();
    }
    @Override
    public String description() {
        return "重新加载本地用户列表";
    }
    @Override
    public String trigger() {
        return "reloadLocUser";
    }
}

// 保存本地用户列表
class saveLocUserCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        return Init.BASEUSERLIST.save();
    }
    @Override
    public String description() {
        return "保存本地用户列表";
    }
    @Override
    public String trigger() {
        return "saveLocUser";
    }
}
