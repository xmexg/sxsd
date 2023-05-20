package org.mex.sxsd_cons.util.myConsoleui;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BaseConsoleui {

    public static String ListPrompt(String title, List options) {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        ListPromptBuilder suUserPrompt = promptBuilder.createListPrompt()
                .name("MyListPrompt")
                .message(title+":")
                .newItem().text("退出").add();
        if(options != null && options.size() != 0) {
            options.forEach((one) -> suUserPrompt.newItem().text((String) one).add());
        }
        suUserPrompt.addPrompt();
        try {
            HashMap<String, ? extends PromtResultItemIF> userChoose = prompt.prompt(promptBuilder.build());//获取用户选择
            String userChooseValue = ConsoleuiResult.ListPrompt(userChoose);
            if(userChooseValue.equals("退出")) {
                return null;
            }
            return userChooseValue;
        } catch (IOException e) {
            return null;
        }
    }
}
