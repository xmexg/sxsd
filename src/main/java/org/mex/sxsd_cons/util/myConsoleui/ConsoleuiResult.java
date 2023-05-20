package org.mex.sxsd_cons.util.myConsoleui;

import de.codeshelf.consoleui.prompt.PromtResultItemIF;

import java.util.HashMap;

/**
 * consoleui的结果转换器
 */
public class ConsoleuiResult {

    /**
     * 返回用户通过ListPrompt选择的值
     * @param listPrompt
     * @return 用户选择的值
     */
    public static String ListPrompt(HashMap<String, ? extends PromtResultItemIF> listPrompt) {
        String result = listPrompt.toString();
        result = result.substring(result.indexOf("selectedId")+12, result.length() - 3);
        return result;
    }
}
