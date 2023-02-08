package wg.application.algorithm.chatgpt;

import java.util.Arrays;

/************************************************************************
 * @author: wg
 * @description: 归并排序 chatGpt 第二版
 * @params:
 * @return:
 * @createTime: 12:53  2023/2/8
 * @updateTime: 12:53  2023/2/8
 ************************************************************************/
public class MergeSort2 {

    public static void main(String[] args) {
        int[] nums = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("排序前：" + Arrays.toString(nums));
        int[] result = mergeSort(nums, 0, nums.length-1);
        System.out.println("排序后：" + Arrays.toString(result));
    }

    private static int[] mergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return new int[]{nums[start]};
        }
        int mid = (start + end) / 2;
        int[] left = mergeSort(nums, start, mid);
        int[] right = mergeSort(nums, mid + 1, end);
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }
}
