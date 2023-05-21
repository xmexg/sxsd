package org.mex.sxsd_cons.command;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import org.mex.sxsd_cons.Console;
import org.mex.sxsd_cons.Init;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.user.BaseUser;
import org.mex.sxsd_cons.answers.user.UserLogin;
import org.mex.sxsd_cons.util.myConsoleui.BaseConsoleui;
import org.mex.sxsd_cons.util.myConsoleui.ConsoleuiResult;

import java.util.List;


public class UserCommand {

    public UserCommand() {
        Console.COMMAND.register(new LoginCommand());
        Console.COMMAND.register(new LogoutCommand());
        Console.COMMAND.register(new UserListCommand());
        Console.COMMAND.register(new SuUserCommand());
        Console.COMMAND.register(new UserAddCommand());
        Console.COMMAND.register(new MyInfoCommand());
    }
}

class LoginCommand implements CommandHandler {
    @Override
    public String trigger() {
        return "userLogin";
    }
    @Override
    public String description() {
        return "用户登录[参数1:手机号]";
    }
    @Override
    public boolean handleCommand(String[] command) {
        String phone;
        if(command.length == 2) {
            phone = command[1];
        } else {
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();
            promptBuilder.createInputPrompt()
                    .name("phone")
                    .message("请输入手机号: ")
                    .defaultValue("")
                    .addPrompt();
            try{
                phone = ConsoleuiResult.InputPrompt(prompt.prompt(promptBuilder.build()));
            } catch (Exception e){
                phone = "";
            }
        }
        if(phone.length() != 11) {
            PrintFormat.println("手机号格式错误", PrintFormat.ERROR);
            return false;
        }
        if(!UserLogin.Send_Validate(phone)) {
            PrintFormat.println("无法发送验证码", PrintFormat.ERROR);
            return false;
        }
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
                .name("validate")
                .message("请输入验证码: ")
                .defaultValue("")
                .addPrompt();
        String validate;
        try{
            validate = ConsoleuiResult.InputPrompt(prompt.prompt(promptBuilder.build()));
        } catch (Exception e){
            validate = "";
        }
        String cookie = UserLogin.Login(phone, validate);
        if(cookie != null) {
            cookie = "userInfo="+cookie;
            PrintFormat.println("登录成功", PrintFormat.OK);
        } else {
            PrintFormat.println("登录失败", PrintFormat.ERROR);
            return false;
        }
        BaseUser baseUser = new BaseUser(phone, cookie);
        Init.BASEUSERLIST.addBaseUser(baseUser);
        return true;
    }
}

class LogoutCommand implements CommandHandler {
    @Override
    public String trigger() {
        return "userLogout";
    }
    @Override
    public String description() {
        return "用户删除[参数1:手机号]";
    }
    @Override
    public boolean handleCommand(String[] command) {
        String phone;
        if(command.length != 2) {
            phone = BaseConsoleui.ListPrompt("请选择要删除的用户", Init.BASEUSERLIST.getBaseUserPhoneList());
        } else{
            phone = command[1];
        }
        if(phone == null || phone.length() != 11) {
            PrintFormat.println("手机号错误", PrintFormat.ERROR);
            return false;
        }
        boolean res = Init.BASEUSERLIST.removeBaseUser(phone);
        if(res) {
            PrintFormat.println("删除成功", PrintFormat.OK);
        } else {
            PrintFormat.println("删除失败", PrintFormat.ERROR);
        }
        return res;
    }
}

class UserListCommand implements CommandHandler {
    @Override
    public String trigger() {
        return "userList";
    }
    @Override
    public String description() {
        return "用户列表";
    }
    @Override
    public boolean handleCommand(String[] command) {
        PrintFormat.println("基础用户列表", PrintFormat.OUT);
        List<BaseUser> baseUserList = Init.BASEUSERLIST.getBaseUserList();
        if(baseUserList == null || baseUserList.size() == 0) {
            PrintFormat.println("  无", PrintFormat.OUT);
            return true;
        }
        for(int i = 0; i < baseUserList.size(); i++) { // 这样写有序号
            PrintFormat.println("  " + (i+1) + " : " + baseUserList.get(i).Phone + " - " + baseUserList.get(i).Cookie, PrintFormat.OUT);
        }
        return true;
    }
}

/**
 * 切换用户
 */
class SuUserCommand implements CommandHandler{
    @Override
    public String trigger() {
        return "su";
    }
    @Override
    public String description() {
        return "切换用户[参数1:该用户的手机号]";
    }
    @Override
    public boolean handleCommand(String[] command) {
        String phone;
        if(command.length != 2) {
            phone = BaseConsoleui.ListPrompt("请选择要切换的用户", Init.BASEUSERLIST.getBaseUserPhoneList());
            if (phone == null) {// 选择了退出
                Init.AUTHUSER.Clear();
                return false;
            }
        } else {
            phone = command[1];
        }
        if(phone == null || phone.length() != 11) {
            PrintFormat.println("手机号存在问题", PrintFormat.ERROR);
            return false;
        }
        BaseUser baseUser = Init.BASEUSERLIST.getBaseUser(phone);
        if(baseUser != null) {
            if(Init.AUTHUSER.Up_User_Info(baseUser)){
                PrintFormat.println("切换成功", PrintFormat.OK);
                return true;
            }
        }
        PrintFormat.println("切换失败", PrintFormat.ERROR);
        return false;
    }
}

class UserAddCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        String phone, cookie;
        if(command.length != 3) {
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();
            promptBuilder.createInputPrompt()
                    .name("phone")
                    .message("请输入手机号: ")
                    .defaultValue("")
                    .addPrompt();
            try{
                phone = ConsoleuiResult.InputPrompt(prompt.prompt(promptBuilder.build()));
            } catch (Exception e){
                phone = "";
            }
            ConsolePrompt prompt2 = new ConsolePrompt();
            PromptBuilder promptBuilder2 = prompt2.getPromptBuilder();
            promptBuilder2.createInputPrompt()
                    .name("phone")
                    .message("请输入cookie: ")
                    .defaultValue("")
                    .addPrompt();
            try{
                cookie = ConsoleuiResult.InputPrompt(prompt2.prompt(promptBuilder2.build()));
            } catch (Exception e){
                cookie = "";
            }
        } else {
            phone = command[1];
            cookie = command[2];
        }
        if(phone.length() != 11 || cookie.length() == 0) {
            PrintFormat.println("数据无效", PrintFormat.ERROR);
            return false;
        }
        BaseUser baseUser = new BaseUser(phone, cookie);
        Init.BASEUSERLIST.addBaseUser(baseUser);
        return true;
    }
    @Override
    public String description() {
        return "通过cookie添加用户[参数1:手机号,参数2:cookie]";
    }
    @Override
    public String trigger() {
        return "userAdd";
    }
}

class MyInfoCommand implements CommandHandler{

    @Override
    public String trigger() {
        return "myInfo";
    }

    @Override
    public String description() {
        return "获取当前用户信息";
    }

    @Override
    public boolean handleCommand(String[] command) {
        if (Init.AUTHUSER.INFO == null) {
            PrintFormat.println("未切换到用户", PrintFormat.OUT);
            return true;
        }
        PrintFormat.println(Init.AUTHUSER.INFO.toString(), PrintFormat.OUT);
        return false;
    }
}
