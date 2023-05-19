package org.mex.sxsd_cons;

import org.mex.sxsd_cons.command.BaseCommand;
import org.mex.sxsd_cons.command.Command;
import org.mex.sxsd_cons.command.StudyCommand;
import org.mex.sxsd_cons.command.UserCommand;

import java.util.Scanner;

public class Console {

    public static Command command = new Command();
    public static Scanner scanner = new Scanner(System.in);

    public void start() {
        Up_Command();
        while (true) {
            PrintFormat.setColor(PrintFormat.LIGHT_BLUE);
            String nowUser = Init.AUTHUSER.PHONE == null ? "root" : Init.AUTHUSER.PHONE;
            PrintFormat.Sprint(" "+nowUser+"@sxsd > ");
            String command = scanner.nextLine().trim();
            PrintFormat.clearColor();
            this.command.execute(command);
        }
    }

    private void Up_Command(){
        new BaseCommand();
        new UserCommand();
        new StudyCommand();
    }
}
