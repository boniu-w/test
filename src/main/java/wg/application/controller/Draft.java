package wg.application.controller;

public class Draft {
    public static void main(String args[]) {
        //int[] arr = {2,1,5,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
        //arraySplitOutput(arr,3);
        String[] arr1 = {"array", "Japan", "America", "China", "Chile", "Brazil", "Korea"};
        arraySplitOutput(arr1, 4);
        System.out.println();
        layout();
    }

    public static void arraySplitOutput(int[] arr, int m) {
        /*
         * arr为输入的数组，m为每行要输出的元素个数
         * */
        int len = arr.length;
        int line = len / m;
        for (int i = 0; i < line; i++) {
            for (int j = m * i; j < m * (i + 1); j++) {
                System.out.print(arr[j] + "   ");
            }
            System.out.println();
        }
        for (int i = m * line; i < len; i++) {
            System.out.print(arr[i] + "    ");
        }
    }

    /****************************************************************
     * 王震宇的方法
     * @author: wg
     * @time: 2020/10/29 10:24
     ****************************************************************/
    public static void arraySplitOutput(String[] arr, int m) {
        /*
         * arr为输入的数组，m为每行要输出的元素个数
         * */
        int len = arr.length;
        int line = len / m;
        for (int i = 0; i < line; i++) {
            for (int j = m * i; j < m * (i + 1); j++) {
                System.out.print(arr[j] + "   ");
            }
            System.out.println();
        }
        for (int i = m * line; i < len; i++) {
            System.out.print(arr[i] + "    ");
        }
    }


    /****************************************************************
     * 徐蛟龙 的方法
     * @author: wg
     * @time: 2020/10/29 10:18
     ****************************************************************/
    public static void layout() {
        String[] arr = {"展昭", "小黑", "二哈", "张无忌", "张三丰", "123", "小323", "二23哈", "张232无忌", "2323三丰"};
        String tr = "";
        for (int i = 0; i < arr.length; i++) {
            tr += " " + arr[i];
            if ((i + 1) % 3 == 0) {
                System.err.println(tr.replaceFirst(",", ""));
                tr = "";
            }
        }
        System.err.println(tr != null ? tr.replaceFirst(",", "") : "");
    }
}
