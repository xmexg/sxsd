package org.mex.sxsd_cons;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.ListPrompt;
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
    public static Scanner SCANNER = new Scanner(System.in);

    public void start() {
        Up_Command();
        while (true) {
            showConsole2();
        }
    }

    private void Up_Command(){
        new BaseCommand();
        new UserCommand();
        new StudyCommand();
    }

    private void showConsole(){
        PrintFormat.setColor(PrintFormat.LIGHT_GREEN);
        String nowUser = Init.AUTHUSER.INFO == null ? "noBody" : Init.AUTHUSER.INFO.get("userName").getAsString();
        PrintFormat.Sprint(" "+nowUser+"@sxsd > ");
        String command = SCANNER.nextLine().trim();
        PrintFormat.clearColor();
        COMMAND.execute(command);
    }

    private void showConsole2(){
        PrintFormat.setColor(PrintFormat.LIGHT_GREEN);
        String nowUser = Init.AUTHUSER.INFO == null ? "noBody" : Init.AUTHUSER.INFO.get("userName").getAsString();
        AnsiConsole.systemInstall();
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        StringsCompleter completer = new StringsCompleter(COMMAND.handlerMap.keySet());
        promptBuilder.createInputPrompt()
                .name("name")
                .message(" "+nowUser+"@sxsd > ")
                .defaultValue("help")
                .addCompleter(completer)
                .addPrompt();
        HashMap<String, ? extends PromtResultItemIF> result = null;
        try {
             result = prompt.prompt(promptBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        COMMAND.execute(ConsoleuiResult.InputPrompt(result));
        PrintFormat.clearColor();
    }
}
