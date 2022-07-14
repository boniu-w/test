// package wg.application.algorithm;
//
// /**
//  * @desc 马踏棋盘算法
//  */
// public class HorseChessboard {
//     private static int X; // 棋盘的列数
//     private static int Y; // 棋盘的行数
//     //创建一个数组，标记棋盘的各个位置是否被访问过
//     private static boolean visited[];
//     //使用一个属性，标记是否棋盘的所有位置都被访问
//     private static boolean finished; // 如果为true,表示成功
//     public static void main(String[] args) {
//         System.out.println("骑士周游算法，开始运行~~");
//         //测试骑士周游算法是否正确
//         X = 8;
//         Y = 8;
//         int row = 1; //马儿初始位置的行，从1开始编号
//         int column = 1; //马儿初始位置的列，从1开始编号
//         //创建棋盘
//         int[][] chessboard = new int[X][Y];
//         visited = new boolean[X * Y];//初始值都是false
//         //测试一下耗时
//         long start = System.currentTimeMillis();
//         traversalChessboard(chessboard, row - 1, column - 1, 1);
//         long end = System.currentTimeMillis();
//         System.out.println("共耗时: " + (end - start) + " 毫秒");
//         //输出棋盘的最后情况
//         for(int[] rows : chessboard) {
//             for(int step: rows) {
//                 System.out.print(step + "\t");
//             }
//             System.out.println();
//         }
//     }
// }
