import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;


public class GraphTraversal {

    static int[][] GRAPH;
    static boolean[] visited;

    public static void DFS(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (visited[current]) {
                continue;
            }

            visited[current] = true;
            System.out.println("Current on (DFS): " + current);

            for (int i = GRAPH.length - 1; i >= 0; i--) {
                if (GRAPH[current][i] == 1 && !visited[i]) {
                    stack.push(i);
                }
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

        if (startNode < 0 || startNode >= numNodes) {
            System.out.println("Invalid start node.");
            return;
        }

        Arrays.fill(visited, false);
        System.out.println("Starting graph traversal using iterative DFS from node " + startNode);
        DFS(startNode);

        Arrays.fill(visited, false);
        System.out.println();

        System.out.println("Starting graph traversal using BFS from node " + startNode);
        BFS(startNode);
    }
}