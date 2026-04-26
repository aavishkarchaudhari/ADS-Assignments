import java.util.LinkedList;
import java.util.Queue;

public class Assignment_6_MazeSolver {

    static int[][] maze = {
            { 1, 0, 1, 1 },
            { 1, 1, 1, 0 },
            { 0, 1, 0, 1 },
            { 1, 1, 1, 1 }
    };

    static boolean[][] visited;

    static int n = maze.length;
    static int m = maze[0].length;

    static int[] dx = { -1, 1, 0, 0 };
    static int[] dy = { 0, 0, -1, 1 };

    public static void BFS(int sx, int sy, int gx, int gy) {

        Queue<int[]> q = new LinkedList<>();
        visited = new boolean[n][m];

        q.add(new int[] { sx, sy });
        visited[sx][sy] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];

            System.out.println("BFS visiting: (" + x + "," + y + ")");

            if (x == gx && y == gy) {
                System.out.println("Reached Goal!");
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m
                        && maze[nx][ny] == 1 && !visited[nx][ny]) {

                    q.add(new int[] { nx, ny });
                    visited[nx][ny] = true;
                }
            }
        }

        System.out.println("No path found");
    }

    public static boolean DFS(int x, int y, int gx, int gy) {

        if (x < 0 || y < 0 || x >= n || y >= m
                || maze[x][y] == 0 || visited[x][y]) {
            return false;
        }

        visited[x][y] = true;
        System.out.println("DFS visiting: (" + x + "," + y + ")");

        if (x == gx && y == gy) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            if (DFS(x + dx[i], y + dy[i], gx, gy)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        int startX = 0, startY = 0;
        int goalX = 3, goalY = 3;

        System.out.println("=== BFS Search ===");
        BFS(startX, startY, goalX, goalY);

        System.out.println("\n=== DFS Search ===");
        visited = new boolean[n][m];
        if (DFS(startX, startY, goalX, goalY)) {
            System.out.println("Reached Goal!");
        } else {
            System.out.println("No path found");
        }
    }
}