package wg.application.algorithm;

import java.util.Arrays;

public class FloydWarshallAlgorithm {
    private static final int INF = Integer.MAX_VALUE;

    public static void floydWarshall(int[][] graph) {
        int n = graph.length;

        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        System.out.println("Shortest distances between all nodes:");
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(dist[i]));
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 10, INF, INF, INF},
            {10, 0, 5, INF, INF},
            {INF, 5, 0, 8, 2},
            {INF, INF, 8, 0, 7},
            {INF, INF, 2, 7, 0}
        };

        floydWarshall(graph);
    }
}