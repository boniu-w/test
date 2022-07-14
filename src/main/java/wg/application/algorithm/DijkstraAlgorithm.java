// package wg.application.algorithm;
//
// /**
//  * @desc 迪杰斯特拉算法
//  * 案例：最短路径问题
//  * 1. 战争时期，胜利乡有7个村庄(A,B,C,D,E,F,G)，现在有6个邮差，从G点出发，需要分别把邮件分别送到A,B,C,D,E,F 六个村庄
//  * 2. 各个村庄的距离用边线表示（权），比如A-B距离5公里
//  * 3. 问：如何计算最短距离
//  */
// public class DijkstraAlgorithm {
//     public static void main(String[] args) {
//         char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
//         int[][] matrix = new int[vertex.length][vertex.length];
//         final int N = 65535;
//         matrix[0] = new int[]{N,5,7,N,N,N,2};
//         matrix[1] = new int[]{5,N,N,9,N,N,3};
//         matrix[2] = new int[]{7,N,N,N,8,N,N};
//         matrix[3] = new int[]{N,9,N,N,N,4,N};
//         matrix[4] = new int[]{N,N,8,N,N,5,4};
//         matrix[5] = new int[]{N,N,N,4,5,N,6};
//         matrix[6] = new int[]{2,3,N,N,4,6,N};
//         // 创建Graph对象
//         Graph graph = new Graph(vertex, matrix);
//         graph.showGraph();
//         // 测试迪杰斯特拉算法
//         graph.dsj(6); // G
//         graph.showDijkstra();
//     }
// }
