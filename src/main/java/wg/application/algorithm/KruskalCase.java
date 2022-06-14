// package wg.application.algorithm;
//
// /**
//  * @desc 克鲁斯卡尔算法
//  * 案例：公交车问题
//  * 1. 某城市新增7个站点，A,B,C,D,E,F,G,现在需要修路7个站点连通
//  * 2. 各个站点距离用连线表示，比如A-B距离12公里
//  * 3. 问：如何修路保证各个站点都能连通，并且总的修建公路总里程最短
//  */
// public class KruskalCase {
//     private static final int INF = Integer.MAX_VALUE;
//     private char[] vertexs;
//     private int[][] matrix;
//     private int edgeNums; // 边的数量
//     public KruskalCase(char[] vertexs,int[][] matrix ) {
//         this.vertexs = vertexs;
//         this.matrix = matrix;
//         // 统计边
//         for (int i = 0; i < vertexs.length; i++) {
//             for (int j = i + 1; j < vertexs.length; j++) { // 每次少一条边，所以是i+1
//                 if (this.matrix[i][j] != INF) {
//                     edgeNums++;
//                 }
//             }
//         }
//     }
//     public static void main(String[] args) {
//         char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
//         int[][] matrix = {
//                      /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
//                 /*A*/{ 0,   12, INF,  INF, INF, 16,  14 },
//                 /*B*/{ 12,  0,   10,  INF, INF, 7,   INF},
//                 /*C*/{ INF, 10,  0,   3,    5,  6,   INF },
//                 /*D*/{ INF, INF, 3,   0,    4,  INF, INF },
//                 /*E*/{ INF, INF, 5,   4,    0,  2,   8 },
//                 /*F*/{ 16,  7,   6,   INF,  2,  0,   9 },
//                 /*G*/{ 14,  INF, INF, INF,  8,  9,   0 }
//         };
//         // 创建KruskalCase对象实例
//         KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
//         //
//         kruskalCase.print();
//         kruskalCase.kruskal();
//     }
// }
