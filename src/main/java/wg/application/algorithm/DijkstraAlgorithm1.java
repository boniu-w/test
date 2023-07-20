package wg.application.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/************************************************************************
 * author: wg
 * description: DijkstraAlgorithm1 
 * createTime: 15:02 2023/7/18
 * updateTime: 15:02 2023/7/18
 ************************************************************************/
public class DijkstraAlgorithm1 {
    private static final int INF = Integer.MAX_VALUE;
    
    public static void dijkstra(int[][] graph, int start) {
        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        
        Set<Integer> visited = new HashSet<>();
        
        while (visited.size() < n) {
            int minDist = INF;
            int minNode = -1;
            
            for (int i = 0; i < n; i++) {
                if (!visited.contains(i) && dist[i] < minDist) {
                    minDist = dist[i];
                    minNode = i;
                }
            }
            
            if (minNode == -1) {
                break;
            }
            
            visited.add(minNode);
            
            for (int i = 0; i < n; i++) {
                if (!visited.contains(i) && graph[minNode][i] != INF) {
                    dist[i] = Math.min(dist[i], dist[minNode] + graph[minNode][i]);
                }
            }
        }
        
        System.out.println("Shortest distances from node " + start + ":");
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + i + ": " + dist[i]);
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
        
        int startNode = 0;
        dijkstra(graph, startNode);
    }
}
