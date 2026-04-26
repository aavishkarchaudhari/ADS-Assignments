import java.util.*;

class Edge {
    int to, weight;

    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class Assignment_5_CampusPrimsPQ {

    static void primMST(int V, Edge[][] graph) {

        int[] routeWeight = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(routeWeight, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        routeWeight[0] = 0;
        parent[0] = -1;

        pq.offer(new int[] { 0, 0 });

        while (!pq.isEmpty()) {

            int u = pq.poll()[0];

            if (visited[u])
                continue;

            visited[u] = true;

            for (Edge e : graph[u]) {

                int v = e.to;

                if (!visited[v] && e.weight < routeWeight[v]) {
                    routeWeight[v] = e.weight;
                    parent[v] = u;
                    pq.offer(new int[] { v, routeWeight[v] });
                }
            }
        }

        String[] dept = { "CSE", "IT", "Mech", "Library", "Admin" };

        int total = 0;
        System.out.println("Minimum Spanning Tree:");

        for (int i = 1; i < V; i++) {
            System.out.println(dept[parent[i]] + " - " + dept[i] + " : " + routeWeight[i]);
            total += routeWeight[i];
        }

        System.out.println("Total Distance: " + total);
    }

    public static void main(String[] args) {

        int V = 5;

        Edge[][] graph = new Edge[V][];

        graph[0] = new Edge[] {
                new Edge(1, 2),
                new Edge(3, 6)
        };

        graph[1] = new Edge[] {
                new Edge(0, 2),
                new Edge(2, 3),
                new Edge(3, 8),
                new Edge(4, 5)
        };

        graph[2] = new Edge[] {
                new Edge(1, 3),
                new Edge(4, 7)
        };

        graph[3] = new Edge[] {
                new Edge(0, 6),
                new Edge(1, 8),
                new Edge(4, 9)
        };

        graph[4] = new Edge[] {
                new Edge(1, 5),
                new Edge(2, 7),
                new Edge(3, 9)
        };

        primMST(V, graph);
    }
}