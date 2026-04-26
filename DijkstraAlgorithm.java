import java.util.PriorityQueue;
import java.util.Arrays;

class Pair {
    int node;
    int distance;

    Pair(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}

public class DijkstraAlgorithm {

    static int[] dijkstra(int V, int[][] graph, int S) {

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[S] = 0;
        pq.add(new Pair(S, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();

            int node = curr.node;
            int dis = curr.distance;

            if (dis > dist[node]) {
                continue;
            }

            for (int adjNode = 0; adjNode < V; adjNode++) {
                int edgeWeight = graph[node][adjNode];

                if (edgeWeight != 0 && dis + edgeWeight < dist[adjNode]) {
                    dist[adjNode] = dis + edgeWeight;
                    pq.add(new Pair(adjNode, dist[adjNode]));
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        int V = 5;

        int[][] graph = {
                { 0, 2, 4, 0, 0 },
                { 2, 0, 1, 7, 0 },
                { 4, 1, 0, 0, 3 },
                { 0, 7, 0, 0, 0 },
                { 0, 0, 3, 0, 0 }
        };

        int source = 0;

        int[] result = dijkstra(V, graph, source);

        System.out.println("Shortest distances from source:");
        for (int i = 0; i < V; i++) {
            System.out.println("Node " + i + " -> " + result[i]);
        }
    }
}