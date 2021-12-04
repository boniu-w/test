package wg.application.io;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        // test();
        testDeleteFile();
    }

    public static void test() {
        // CorrosionAssessmentHistoryEntity entity = baseDao.selectById("");
        // String reportContent = entity.getReportContent();

        // 创建文件
        String reportContent = "11111";
        String path = "C:\\Users\\wg\\Desktop\\wg\\"; // 最后的 双反斜线 可写 可不写
        String fileName = "wg.md";
        File file = new File(path, fileName);

        if (!file.exists()) {
            file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 把这个 写成 md , 写到服务器 指定位置
        StringReader stringReader = new StringReader(reportContent);
        try {
            BufferedReader bufferedReader = new BufferedReader(stringReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            char[] chars = new char[1024];
            int len;

            while ((len = bufferedReader.read(chars)) != -1) {
                System.out.println("read " + len + " char.");
                bufferedWriter.write(chars, 0, len);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @description: 虽然 删除成功, 但是, file 不为 null
     * @author: wg
     * @date: 18:19  2021/11/25
     * @params:
     * @return:
     ************************************************************************/
    public static void testDeleteFile() {
        File file = new File("D:/123.txt");
        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("yi shan chu");
        }
        System.out.println(file.getName());
    }
}
