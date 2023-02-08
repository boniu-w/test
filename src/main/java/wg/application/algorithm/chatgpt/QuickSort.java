package wg.application.algorithm.chatgpt;

import java.util.Arrays;

/************************************************************************
 * @author: wg
 * @description: 快速排序 chatGpt
 * @params:
 * @return:
 * @createTime: 10:09  2023/2/8
 * @updateTime: 10:09  2023/2/8
 ************************************************************************/
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {4, 2, 9, 6, 23, 12, 34, 0, 1};
        quickSort(arr, 0, arr.length-1);
        System.out.println("快速排序结果：" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length <= 0) return;
        if (low >= high) return;
        int pivot = partition(arr, low, high);
        quickSort(arr, low, pivot - 1);
        quickSort(arr, pivot + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }
}
