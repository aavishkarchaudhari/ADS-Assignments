import java.util.*;

public class GraphTraversal {

    static int[][] GRAPH;
    static boolean[] visited;

    public static void DFS(int current) {
        visited[current] = true;
        System.out.println("Current on (DFS): " + current);

        for (int i = 0; i < GRAPH.length; i++) {
            if (GRAPH[current][i] == 1 && !visited[i]) {
                DFS(i);
            }
        }
    }

    public static void BFS(int start) {
        Queue<Integer> q = new LinkedList<>();
        visited[start] = true;
        q.add(start);

        while (!q.isEmpty()) {
            int current = q.poll();

            System.out.println("Current on (BFS): " + current);

            for (int i = 0; i < GRAPH.length; i++) {
                if (GRAPH[current][i] == 1 && !visited[i]) {
                    q.add(i);
                    visited[i] = true;
                }
            }
        }
    }

    public static void main(String[] args) {

        GRAPH = new int[][] {
                { 1, 0, 1, 1, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 1, 1, 0, 1 },
                { 0, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int numNodes = GRAPH.length;
        visited = new boolean[numNodes];

        int startNode = 0;

        System.out.println("Starting maze and finding min path using DFS from node " + startNode);
        DFS(startNode);

        Arrays.fill(visited, false);
        System.out.println();

        System.out.println("Starting maze and finding min path using BFS from node " + startNode);
        BFS(startNode);
    }
}