package org.mex.sxsd_cons;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.console.completer.StringsCompleter;
import org.fusesource.jansi.AnsiConsole;
import org.mex.sxsd_cons.command.BaseCommand;
import org.mex.sxsd_cons.command.Command;
import org.mex.sxsd_cons.command.StudyCommand;
import org.mex.sxsd_cons.command.UserCommand;
import org.mex.sxsd_cons.util.myConsoleui.ConsoleuiResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Console {

    public static Command COMMAND = new Command();
    private static final Scanner SCANNER = new Scanner(System.in);// 过时的方法,不应该再使用
    private static ConsolePrompt PROMPT;

    public void start() {
        Up_Command();
        AnsiConsole.systemInstall();
        PROMPT = new ConsolePrompt();
        while (true) {
            showConsole2();
        }
    }

    private void Up_Command(){
        new BaseCommand();
        new UserCommand();
        new StudyCommand();
    }

    /**
     * 显示控制台
     * 过时的控制台,不应该再使用
     */
    private void showConsole(){
        PrintFormat.setColor(PrintFormat.LIGHT_GREEN);
        String nowUser = Init.AUTHUSER.INFO == null ? "noBody" : Init.AUTHUSER.INFO.get("userName").getAsString();
        PrintFormat.Sprint(" "+nowUser+"@sxsd > ");
        String command = SCANNER.nextLine().trim();
        PrintFormat.clearColor();
        COMMAND.execute(command);
    }

    /**
     * 显示控制台
     * 正在使用的控制台
     */
    private void showConsole2(){
        PrintFormat.setColor(PrintFormat.LIGHT_GREEN);
        String nowUser = Init.AUTHUSER.INFO == null ? "noBody" : Init.AUTHUSER.INFO.get("userName").getAsString();

        PromptBuilder promptBuilder = PROMPT.getPromptBuilder();
        StringsCompleter completer = new StringsCompleter(COMMAND.handlerMap.keySet());
        promptBuilder.createInputPrompt()
                .name("name")
                .message(" "+nowUser+"@sxsd > ")
                .defaultValue("help")
                .addCompleter(completer)
                .addPrompt();
        HashMap<String, ? extends PromtResultItemIF> result = null;
        try {
             result = PROMPT.prompt(promptBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        COMMAND.execute(ConsoleuiResult.InputPrompt(result));
        PrintFormat.clearColor();
    }
}
