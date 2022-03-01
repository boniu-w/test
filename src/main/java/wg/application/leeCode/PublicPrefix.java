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
    public static void main(String[] args) {
        String[] strs = {"baa", "bbb", "bba"};
        String longestCommonPrefix = longestCommonPrefix1(strs);
        System.out.println(longestCommonPrefix);

        BinarySearch binarySearch = new BinarySearch();
        String s = binarySearch.longestCommonPrefix(strs);
        System.out.println(s);

        DivideAndConquer divideAndConquer = new DivideAndConquer();
        String commonPrefix = divideAndConquer.longestCommonPrefix(strs);
        System.out.println(commonPrefix);
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
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

    /************************************************************************
     * @description: 纵向扫描
     * @author: wg
     * @date: 10:36  2021/10/11
     ************************************************************************/
    public static String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int length = strs[0].length();
        int count = strs.length;
        for (int i = 0; i < length; i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < count; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    /************************************************************************
     * @description: 二分查找
     * @author: wg
     * @date: 10:39  2021/10/11
     ************************************************************************/
    static class BinarySearch {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }
            int minLength = Integer.MAX_VALUE;
            for (String str : strs) {
                minLength = Math.min(minLength, str.length());
            }
            int low = 0, high = minLength;
            while (low < high) {
                int mid = (high - low + 1) / 2 + low;
                if (isCommonPrefix(strs, mid)) {
                    low = mid;
                } else {
                    high = mid - 1;
                }
            }
            return strs[0].substring(0, low);
        }

        public boolean isCommonPrefix(String[] strs, int length) {
            String str0 = strs[0].substring(0, length);
            int count = strs.length;
            for (int i = 1; i < count; i++) {
                String str = strs[i];
                for (int j = 0; j < length; j++) {
                    if (str0.charAt(j) != str.charAt(j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }


    /************************************************************************
     * @description: 分治
     * @author: wg
     * @date: 10:42  2021/10/11
     ************************************************************************/
    static class DivideAndConquer {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            } else {
                return longestCommonPrefix(strs, 0, strs.length - 1);
            }
        }

        public String longestCommonPrefix(String[] strs, int start, int end) {
            if (start == end) {
                return strs[start];
            } else {
                int mid = (end - start) / 2 + start;
                String lcpLeft = longestCommonPrefix(strs, start, mid);
                String lcpRight = longestCommonPrefix(strs, mid + 1, end);
                return commonPrefix(lcpLeft, lcpRight);
            }
        }

        public String commonPrefix(String lcpLeft, String lcpRight) {
            int minLength = Math.min(lcpLeft.length(), lcpRight.length());
            for (int i = 0; i < minLength; i++) {
                if (lcpLeft.charAt(i) != lcpRight.charAt(i)) {
                    return lcpLeft.substring(0, i);
                }
            }
            return lcpLeft.substring(0, minLength);
        }
    }
}
