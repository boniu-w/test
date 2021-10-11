package wg.application.leeCode;

/************************************************************************
 * @description: 公共前缀
 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * @author: wg
 * @date: 17:41  2021/10/9
 ************************************************************************/
public class PublicPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        String ans = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            for (; j < ans.length() && j < strs[i].length(); j++) {
                if (ans.charAt(j) != strs[i].charAt(j))
                    break;
            }
            ans = ans.substring(0, j);
            if (ans.equals(""))
                return ans;
        }
        return ans;
    }
}
