package org.mex.sxsd_cons;

import org.mex.sxsd_cons.answers.user.AuthUser;
import org.mex.sxsd_cons.command.BaseCommand;
import org.mex.sxsd_cons.command.Command;
import org.mex.sxsd_cons.command.StudyCommand;
import org.mex.sxsd_cons.command.UserCommand;

import java.util.Scanner;

public class Console {

    public static Command COMMAND = new Command();
    public static Scanner SCANNER = new Scanner(System.in);

    public void start() {
        Up_Command();
        while (true) {
            showConsole();
        }
    }

    private void Up_Command(){
        new BaseCommand();
        new UserCommand();
        new StudyCommand();
    }

    private void showConsole(){
        PrintFormat.setColor(PrintFormat.LIGHT_BLUE);
        String nowUser = Init.AUTHUSER.INFO == null ? "noBody" : Init.AUTHUSER.INFO.get("userName").getAsString();
        PrintFormat.Sprint(" "+nowUser+"@sxsd > ");
        String command = SCANNER.nextLine().trim();
        PrintFormat.clearColor();
        COMMAND.execute(command);
    }
}
