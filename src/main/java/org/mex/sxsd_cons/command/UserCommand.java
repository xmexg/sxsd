package org.mex.sxsd_cons.command;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import org.mex.sxsd_cons.Console;
import org.mex.sxsd_cons.Init;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.user.BaseUser;
import org.mex.sxsd_cons.answers.user.UserLogin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.mex.sxsd_cons.Console.scanner;

public class UserCommand {

    public UserCommand() {
        Console.command.register(new LoginCommand());
        Console.command.register(new LogoutCommand());
        Console.command.register(new UserListCommand());
        Console.command.register(new SuUserCommand());
        Console.command.register(new UserAddCommand());
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
        PrintFormat pf = new PrintFormat(PrintFormat.INPUT);
        if(command.length == 2) {
            phone = command[1];
        } else {
            pf.print(" + 请输入手机号: ");
            phone = scanner.nextLine().trim();
        }
        if(phone.length() != 11) {
            PrintFormat.println("手机号格式错误", PrintFormat.ERROR);
            return false;
        }
        if(!UserLogin.Send_Validate(phone)) {
            PrintFormat.println("无法发送验证码", PrintFormat.ERROR);
            return false;
        }
        pf.print(" + 请输入验证码: ");
        String validate = scanner.nextLine().trim();
        String cookie = UserLogin.Login(phone, validate);
        if(cookie != null) {
            PrintFormat.println("登录成功", PrintFormat.OK);
        } else {
            PrintFormat.println("登录失败", PrintFormat.ERROR);
            return false;
        }
        BaseUser baseUser = new BaseUser(phone, cookie);
        System.out.println(baseUser.Cookie);
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
            PrintFormat.print(" + 请输入手机号: ", PrintFormat.INPUT);
            phone = scanner.nextLine().trim();
        } else{
            phone = command[1];
        }
        if(phone.length() != 11) {
            PrintFormat.println("手机号格式错误", PrintFormat.ERROR);
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
//          PrintFormat.println("  " + (i+1) + " : " + this.baseUserList.get(i).Phone + " - " + this.baseUserList.get(i).Cookie, PrintFormat.OUT);
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
            List<BaseUser> baseuserList = Init.BASEUSERLIST.getBaseUserList();
            ConsolePrompt prompt = new ConsolePrompt();
            PromptBuilder promptBuilder = prompt.getPromptBuilder();
            ListPromptBuilder suUserPrompt = promptBuilder.createListPrompt()
                    .name("suUser")
                    .message("请选择要切换的用户(j:向上,k:向下,回车:确认):")
                    .newItem().text("退出").add();
            if(baseuserList != null && baseuserList.size() != 0) {
                baseuserList.forEach(baseUser -> suUserPrompt.newItem().text(baseUser.Phone).add());
            }
            suUserPrompt.addPrompt();
            try {
                HashMap<String, ? extends PromtResultItemIF>  userChoose = prompt.prompt(promptBuilder.build());//获取用户选择
                System.out.println("用户选择的是:" + userChoose.get("suUser"));
                return true;// 测试
            } catch (IOException e) {
                PrintFormat.println("切换用户失败", PrintFormat.ERROR);
                return false;
            }
        } else {
            phone = command[1];
        }
        BaseUser baseUser = Init.BASEUSERLIST.getBaseUser(phone);
        if(baseUser != null) {
            if(Init.AUTHUSER.Up_User_Info(baseUser.Cookie)){
                PrintFormat.println("切换成功", PrintFormat.OK);
                return true;
            }
        }
        return false;
    }
}

class UserAddCommand implements CommandHandler{

    @Override
    public boolean handleCommand(String[] command) {
        String phone, cookie;
        if(command.length != 3) {
            PrintFormat.print(" + 请输入手机号: ", PrintFormat.INPUT);
            phone = scanner.nextLine().trim();
            PrintFormat.print(" + 请输入cookie: ", PrintFormat.INPUT);
            cookie = scanner.nextLine().trim();
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
