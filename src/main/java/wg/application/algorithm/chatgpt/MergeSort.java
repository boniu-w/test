package wg.application.algorithm.chatgpt;

import java.util.Arrays;

/************************************************************************
 * @author: wg
 * @description: 归并算法(chatGpt)
 * 归并算法（Merge Sort）是分治算法的一种。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，
 * 再使子序列段间有序。若将两个有序表合并成一个有序表，称为二路归并。
 * @params:
 * @return:
 * @createTime: 12:49  2023/2/8
 * @updateTime: 12:49  2023/2/8
 ************************************************************************/
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {2, 1, 6, 5, 9, 8, 2020, 199};

        // 测试 i++ 与 ++i
        int t=0;
        int temp= arr[t++];
        System.out.println("temp = " + temp); // 2
        System.out.println("t = " + t); // 1
        for (int i = 0; i < arr.length; ) {
            System.out.println("arr[i++] = " + arr[i++]);
        }

        sort(arr);
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
    }

    private static void sort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid, temp); // 左侧排序
            sort(arr, mid + 1, right, temp); // 右侧排序
            merge(arr, left, mid, right, temp); // 与临时数组合并(真正的排序)
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        t = 0;
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }
}
