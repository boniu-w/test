package wg.application.tool;

import wg.application.util.CommonUtil;
import wg.application.util.StringUtil;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 11:26 2022/7/5
 * @updateTime: 11:26 2022/7/5
 ************************************************************************/
public class Tools {

    /************************************************************************
     * @author: wg
     * @description: 解密密码
     * @params:
     * @return:
     * @createTime: 10:11  2022/7/11
     * @updateTime: 10:11  2022/7/11
     ************************************************************************/
    public static void encodePassword() {
        String password = "aa6a5a0b33cfdaaf0c942939ad6d5e08150236abd95312aad4a94a39fec7315b";
        String s = CommonUtil.passwordEncode(password);
        System.out.println(s);

    }

    public static void main(String[] args) {
        encodePassword();
    }

    /************************************************************************
     * @author: wg
     * @description: 字符串 转 unicode
     * @params:
     * @return:
     * @createTime: 10:13  2022/7/11
     * @updateTime: 10:13  2022/7/11
     ************************************************************************/
    public String stringToUnicode(String str) {
        return StringUtil.toUnicode(str);
    }

    /************************************************************************
     * @author: wg
     * @description: unicode 转 字符串
     * @params:
     * @return:
     * @createTime: 10:14  2022/7/11
     * @updateTime: 10:14  2022/7/11
     ************************************************************************/
    public String unicodeToString(String unicode){
        return StringUtil.decodeUnicode(unicode);
    }
}
