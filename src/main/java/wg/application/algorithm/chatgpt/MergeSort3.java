package wg.application.algorithm.chatgpt;

/************************************************************************
 * @author: wg
 * @description: 归并排序 chatGpt 第三版
 * @params:
 * @return:
 * @createTime: 12:56  2023/2/8
 * @updateTime: 12:56  2023/2/8
 ************************************************************************/
public class MergeSort3 {
    public static void mergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }
    public static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= end) {
            temp[k++] = arr[j++];
        }
        for (i = 0; i < k; i++) {
            arr[start + i] = temp[i];
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[] {3, 5, 2, 4, 1};
        mergeSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
