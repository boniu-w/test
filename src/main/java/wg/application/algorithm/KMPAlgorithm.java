// package wg.application.algorithm;
//
// import static org.apache.lucene.analysis.compound.hyphenation.TernaryTree.strlen;
//
// /**
//  * @desc KMP算法
//  * 基本介绍：
//  * （1）暴力匹配算法
//  * 1）如果当前字符匹配成功（即str1[i]=str2[i]），则i++,j++，继续匹配下一个字符
//  * 2）如果失败，令i=i-(j-1)，j=0，相当于每次匹配失败时，i回溯，j被转为0
//  * 3）用暴力方法解决的话就会有大量的回溯，每次只移动一位，若是不匹配，移动到下一位接着判断，浪费大量时间。（不可行）
//  * 4）暴力匹配实现
//  * （2）KMP算法介绍
//  * 1）KMP是一个解决模式串在文本串是否出现过，如果出现过，最早出现的位置就经典算法。
//  * 2）Knuth-Morris-Pratt字符串查找法，简称KMP。
//  * 3）KMP算法就是利用之前判断过信息，通过一个next数组，保存模式串中前后最长公共序列的长度，每次回溯时，通过next数组找到，
//  * 前面匹配的位置，省去了大量的计算时间
//  */
// public class KMPAlgorithm {
//     public static void main(String[] args) {
//         // 暴力匹配
//         String str1 = "ABCDE";
//         String str2 = "CD";
//         int index = violenceMatch(str1, str2);
//         if (index != -1) {
//             System.out.println("找到了，位置：" + index);
//         } else {
//             System.out.println("没有找到！");
//         }
//         // KMP算法介绍
//         // 字符串模板匹配值
//         str1 = "BBC ABCDAD ABCDABCDABDE";
//         str2 = "ABCDABD";
//         /*int[] next = kmpNext("ABCDABD");
//         System.out.println("next=" + Arrays.toString(next));*/
//         index = kmpMatch(str1, str2, kmpNext(str2));
//         if (index != -1) {
//             System.out.println("找到了，位置：" + index);
//         } else {
//             System.out.println("没有找到！");
//         }
//     }
//
//
//     static int ViolentMatch(char*s, char*p) {
//         int sLen = strlen(s);
//         int pLen = strlen(p);
//
//         int i = 0;
//         int j = 0;
//         while (i < sLen && j < pLen) {
//             if (s[i] == p[j]) {
//                 //①如果当前字符匹配成功（即S[i] == P[j]），则i++，j++
//                 i++;
//                 j++;
//             } else {
//                 //②如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0
//                 i = i - j + 1;
//                 j = 0;
//             }
//         }
//         //匹配成功，返回模式串p在文本串s中的位置，否则返回-1
//         if (j == pLen)
//             return i - j;
//         else
//             return -1;
//     }
//
//     static int KmpSearch(char[] s, char[] p) {
//         int i = 0;
//         int j = 0;
//         int sLen = strlen(s);
//         int pLen = strlen(p);
//         while (i < sLen && j < pLen) {
//             //①如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++
//             if (j == -1 || s[i] == p[j]) {
//                 i++;
//                 j++;
//             } else {
//                 //②如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]
//                 //next[j]即为j所对应的next值
//                 j = next[j];
//             }
//         }
//         if (j == pLen)
//             return i - j;
//         else
//             return -1;
//     }
// }
