package wg.application.cmd;

import java.io.*;
import java.util.Map;

public class CmdTest {
    public static void main(String[] args) {
        testRuntime();
        // runCmdCommand1();
        // runCmdCommand2();
    }

    public static void runCmdCommand() {
        String command = "cmd.exe /c dir";
        try {
            Process process = Runtime.getRuntime().exec(command);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testProcessBuilder() {
        String[] command = new String[]{"E:\\pandoc-2.16.1\\pandoc", "d:\\wg\\wg.md", "-o", "D:\\wg\\wg.docx"};
        File file = new File("E:\\pandoc-2.16.1");

        // String[] string1 = {"mkdir", "wg-test"};
        // ProcessBuilder processBuilder1 = new ProcessBuilder(string1);
        // processBuilder1.directory(file);
        // processBuilder1.redirectErrorStream(true);
        // try {
        //     Process process = processBuilder1.start();
        //     int i = process.waitFor();
        // } catch (IOException | InterruptedException e) {
        //     e.printStackTrace();
        // }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.directory(file); //设置工作目录
        processBuilder.redirectErrorStream(true);

        Map<String, String> environment = processBuilder.environment();
        System.out.println("---------👇 environment 👇----------------");
        environment.forEach((k, v) -> System.out.println(k + "=" + v));

        // List<String> commandList = processBuilder.command();
        // commandList.forEach(System.out::println);

        // File directory = processBuilder.directory();
        System.out.println("directory -->> " + processBuilder.directory());

        try {
            Process process = processBuilder.start();
            int i = process.waitFor();
            System.out.println(i);

            synchronized (InputStream.class) {
                InputStream inputStream = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
                inputStream.close();
                System.out.println("---inputStream---");
            }
            int i1 = process.waitFor();
            System.out.println(i1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runCmdCommand2() {
        SecurityManager security = System.getSecurityManager();
        System.out.println(security);
    }

    /************************************************************************
     * @description: 成功的测试
     * @author: wg
     * @date: 16:47  2021/11/23
     * @params:
     * @return:
     ************************************************************************/
    public static void testRuntime() {
        String pandocDir = "E:\\pandoc-2.16.1\\";
        String redirectDir = "E:\\pandoc-2.16.1\\test\\";
        String command = pandocDir + "pandoc test.md -o " + redirectDir + "wg.docx";
        File pandocFile = new File("E:\\pandoc-2.16.1");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command, null, pandocFile);
            int i = process.waitFor();
            System.out.println(i);

            // synchronized (InputStream.class) {
            //     InputStream inputStream = process.getInputStream();
            //     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            //     String line = br.readLine();
            //     while (line != null) {
            //         System.out.println(line);
            //         line = br.readLine();
            //     }
            //     inputStream.close();
            //     System.out.println("---success---");
            // }
            // synchronized (InputStream.class) {
            //     InputStream errorStream = process.getErrorStream();
            //     BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
            //     String line = br.readLine();
            //     while (line != null) {
            //         System.out.println(line);
            //         line = br.readLine();
            //     }
            //     errorStream.close();
            //     System.out.println("----errorStream----");
            // }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
