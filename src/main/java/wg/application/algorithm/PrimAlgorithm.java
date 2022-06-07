// package wg.application.algorithm;
//
// /**
//  * @desc 普利姆算法
//  * 应用案例：修路问题
//  * <p>
//  * 思路分析
//  *  1.从<A>顶点开始处理=><A,G> 2
//  *      A,C[7] A-G[2] A-B[5] =>
//  *  2.<A,G>开始，将A和G顶点和他们相邻的还没有访问的顶面进行处理=> <A,G,B>
//  *      A-C[7] A-B[5] G-B[3] G-F[6]
//  *  3.<A,G,B>开始，将A,G,B顶点和他们相邻的还没有访问的顶面进行处理=> <A,G,B>
//  *      A-C[7] G-E[4] G-F[6] B-D[9]
//  *  ...
//  *  4.{A,G,B,E,F,D} -> C // 第6次大循环，对应边<A,C>权值：7 => <A,G,B,E,F,D,C>
//  */
// public class PrimAlgorithm {
//     public static void main(String[] args) {
//         char[] data = {'A','B','C','D','E','F','G'};
//         int verxs = data.length;
//         // 邻接矩阵
//         int[][] weight = new int[][] {
//                 {10000,5,7,10000,10000,10000,2},
//                 {5,10000,10000,9,10000,10000,3},
//                 {7,10000,10000,10000,8,10000,10000},
//                 {10000,9,10000,10000,10000,4,10000},
//                 {10000,10000,8,10000,10000,5,4},
//                 {10000,10000,10000,4,5,10000,6},
//                 {2,3,10000,10000,4,6,10000}
//         };
//         // 创建MGraph对象
//         MGraph graph = new MGraph(verxs);
//         // 创建最小树
//         MinTree minTree = new MinTree();
//         minTree.createGraph(graph, verxs, data, weight);
//         // 输出
//         minTree.showGraph(graph);
//         // 测试普利姆算法
//         minTree.prim(graph, 0);
//     }
// }
