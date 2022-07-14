package wg.application.util;

import java.io.*;

public class ImageUtil {

    /************************************************************************
     * @author: wg
     * @description: 图片转16进制
     * @params: fileTxt 文件的路径+文件名 ;  fileImage 图片的路径+图片名
     * @return:
     * @createTime: 10:33  2022/3/4
     * @updateTime: 10:33  2022/3/4
     ************************************************************************/
    public static void imageToHex(String fileImage, String fileTxt) {
        try {
            FileInputStream fis = new FileInputStream(fileImage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            // 得到图片的字节数组
            byte[] result = bos.toByteArray();
            // 字节数组转成十六进制
            String str = MathUtil.bytesToHexString(result);
            System.out.println("++++  " + str);

            // 将十六进制串保存到txt文件中
            PrintWriter pw = new PrintWriter(new FileWriter(fileTxt));
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************************************
     * @author: wg
     * @description: 16进制转图片
     * @params: fileTxt 文件的路径+文件名 ;  fileImage 图片的路径+图片名
     * @return:
     * @createTime: 10:33  2022/3/4
     * @updateTime: 10:33  2022/3/4
     ************************************************************************/
    public static void hexToImage(String fileTxt, String fileImage) {
        try {
            InputStream is = new FileInputStream(fileTxt);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                sb.append(str);
            }
            saveToImg(sb.toString().toUpperCase(), fileImage);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void saveToImg(String src, String output) {
        if (src == null || src.length() == 0) {
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            byte[] bytes = src.getBytes();
            for (int i = 0; i < bytes.length; i += 2) {
                out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现字节数组向十六进制的转换方法一
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    private static int charToInt(byte ch) {
        int val = 0;
        if (ch >= 0x30 && ch <= 0x39) {
            val = ch - 0x30;
        } else if (ch >= 0x41 && ch <= 0x46) {
            val = ch - 0x41 + 10;
        }
        return val;
    }
}
