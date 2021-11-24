package wg.application.cmd;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CmdTest {
    public static void main(String[] args) {
        test3();
        // runCmdCommand1();
    }

    public static void runCmdCommand() {
        String command = "cmd.exe /c dir";
        try {
            Process process = Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runCmdCommand1() {
        String[] command = new String[10];
        command[0] = "cmd.exe cd /d:";
        command[1] = "d:";
        command[2] = "where /R c: wxwork.exe";
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        Map<String, String> environment = processBuilder.environment();
        environment.forEach((k, v) -> System.out.println(k + "=" + v));

        // List<String> commandList = processBuilder.command();
        // commandList.forEach(System.out::println);

        // ProcessBuilder processBuilder = new ProcessBuilder(command);
        // processBuilder.directory(new File("C:\\Users\\"));//设置工作目录

        File directory = processBuilder.directory();
        System.out.println("directory -->> " + directory);

        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runCmdCommand2() {
        List<String> commands = new ArrayList<>();
        commands.add("cmd.exe");
        commands.add("cd");
        commands.add("E:\\pandoc-2.16.1");
        commands.add("e:");
        commands.add("pandoc -v");

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
    }

    /************************************************************************
     * @description: 成功的测试
     * @author: wg
     * @date: 16:47  2021/11/23
     * @params:
     * @return:
     ************************************************************************/
    public static void test3() {
        // String command = "ping baidu.com";
        String command = "cmd /c dir";
        // command = "pandoc test.md -o wg.docx";
        String[] commands = new String[]{"pandoc", "test.md", "-o", "wg.docx"};
        File file = new File("E:\\pandoc-2.16.1");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command, null, null);
            synchronized (InputStream.class) {
                InputStream inputStream = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
                inputStream.close();
            }
            synchronized (InputStream.class) {
                InputStream errorStream = process.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
                errorStream.close();
                System.out.println("----errorStream----");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
