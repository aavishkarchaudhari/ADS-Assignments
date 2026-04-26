import java.util.PriorityQueue;
import java.util.Arrays;

public class PrimsAlgorithm {

    static void primMST(int V, int[][] graph) {

        int[] key = new int[V]; // min edge weight
        int[] parent = new int[V]; // MST
        boolean[] visited = new boolean[V];

        Arrays.fill(key, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        key[0] = 0;
        parent[0] = -1;

        pq.offer(new int[] { 0, 0 });

        while (!pq.isEmpty()) {

            int u = pq.poll()[0];

            if (visited[u])
                continue;

            visited[u] = true;

            for (int v = 0; v < V; v++) {

                if (!visited[v] && graph[u][v] != 0 && graph[u][v] < key[v]) {
                    key[v] = graph[u][v];
                    parent[v] = u;
                    pq.offer(new int[] { v, key[v] });
                }
            }
        }

        // Print MST
        int total = 0;
        System.out.println("Edges in MST:");

        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + " : " + key[i]);
            total += key[i];
        }

        System.out.println("Total Weight: " + total);
    }

    public static void main(String[] args) {

        int V = 5;

        int[][] graph = {
                { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 }
        };

        primMST(V, graph);
    }
}