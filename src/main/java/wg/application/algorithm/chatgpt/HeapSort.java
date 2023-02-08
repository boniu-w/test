package wg.application.algorithm.chatgpt;

import java.util.Arrays;

/************************************************************************
 * @author: wg
 * @description: chatGpt 写的 堆排序
 * @params:
 * @return:
 * @createTime: 9:40  2023/2/8
 * @updateTime: 9:40  2023/2/8
 ************************************************************************/
public class HeapSort {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 5, 1, 4, 2};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums) {
        int n = nums.length;

        // 建堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // 排序
        for (int i = n - 1; i >= 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            heapify(nums, i, 0);
        }
    }

    public static void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }

        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;
            heapify(nums, n, largest);
        }
    }
}
