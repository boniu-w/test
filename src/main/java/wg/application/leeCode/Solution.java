package wg.application.leeCode;

import java.util.*;

/************************************************************************
 * @description: 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 * @author: wg
 * @date: 15:38  2021/10/9
 ************************************************************************/
public class Solution {

    public static void main(String[] args) {
        int[] nums = new int[100];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = new Double(Math.random() * 9).intValue();
        }
        System.out.println(Arrays.toString(nums));

        System.out.println(System.currentTimeMillis());
<<<<<<< HEAD
//        int[] ints1 = twoSum(ints, 9);
        int[] ints1 = test2(ints, 9);
        System.out.println(Arrays.toString(ints1));
=======
        int[] index = twoSum(nums, 9);
        // int[] index = test2(nums, 9);
        System.out.println(Arrays.toString(index));
        for (int i = 0; i < 2; i++) {
            int anInt = nums[index[i]];
            System.out.println(anInt);
        }
>>>>>>> e38a34d08cb83702f82fc77eeffa2242990b1106
        System.out.println(System.currentTimeMillis());
    }

    /************************************************************************
     * @description: 两数之和
     * @author: wg
     * @date: 17:11  2021/10/8
     ************************************************************************/
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    public static int[] test2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (map.containsKey(target - nums[i])) {
                System.out.println(i + "  " + nums[i]);
                map.forEach((key, value) -> {
                    System.out.println("key -> " + key);
                    System.out.println("value -> " + value);
                    System.out.println();
                });
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }
}
