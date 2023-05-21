package org.mex.sxsd_cons.command;

import org.mex.sxsd_cons.PrintFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令行
 */
public class Command {
	public Map<String, CommandHandler> handlerMap;

	public Command() {
		this.handlerMap = new HashMap<>();
	}

	public void register(CommandHandler handler) {
		this.handlerMap.put(handler.trigger(), handler);
	}

	public boolean execute(String command) {
		if (command == null) {
			PrintFormat.println("空指令", PrintFormat.ERROR);
			return false;
		}
		command = command.trim();
		if(command.equals("")) return false;
		String[] cmd = command.split("\\s+");
		String cmd0 = cmd[0];
		CommandHandler handler = this.handlerMap.get(cmd0);
		if(handler != null) {
			return handler.handleCommand(cmd);
		} else {
			PrintFormat.println("未知命令: " + cmd0, PrintFormat.ERROR);
		}
		return false;
	}

}
