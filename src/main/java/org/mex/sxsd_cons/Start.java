package org.mex.sxsd_cons;

public class Start {
	
	static String LOGO = "   ,-,--.           ,-.--,    ,-,--.\n"
			+ " ,-.'-  _\\ .--.-.  /=/, .'  ,-.'-  _\\   _,..---._\n"
			+ "/==/_ ,_.' \\==\\ -\\/=/- /   /==/_ ,_.' /==/,   -  \\\n"
			+ "\\==\\  \\     \\==\\ `-' ,/    \\==\\  \\    |==|   _   _\\\n"
			+ " \\==\\ -\\     |==|,  - |     \\==\\ -\\   |==|  .=.   |\n"
			+ " _\\==\\ ,\\   /==/   ,   \\    _\\==\\ ,\\  |==|,|   | -|\n"
			+ "/==/\\/ _ | /==/, .--, - \\  /==/\\/ _ | |==|  '='   /\n"
			+ "\\==\\ - , / \\==\\- \\/=/ , /  \\==\\ - , / |==|-,   _`/\n"
			+ " `--`---'   `--`-'  `--`    `--`---'  `-.`.____.'\n"
			+ "";


    public static void main(String[] args) {
    	
    	PrintFormat.println(LOGO, PrintFormat.GREEN);
        PrintFormat.println("Hello!\nIf garbled,please use -Dfile.encoding=utf-8 ", PrintFormat.LIGHT_GREEN);
        PrintFormat.println("书香山东答题程序\n你可以使用 help 查看使用帮助", PrintFormat.LIGHT_GREEN);

		new Init();// 初始化
        new Console().start();// 启动控制台
    }
}
