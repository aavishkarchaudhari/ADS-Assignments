import java.util.*;

class Department {
    String name;
    String location;

    Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
}

public class MST_Prims {

    static int findMinKey(int[] key, boolean[] mstSet, int V) {
        int minVal = Integer.MAX_VALUE;
        int index = -1;

        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < minVal) {
                minVal = key[v];
                index = v;
            }
        }
        return index;
    }

    static void printMST(int[] parent, int[][] graph, Department[] departments, int V) {
        System.out.println("\nMinimum Spanning Tree Connections:");
        System.out.println("---------------------------------");

        int totalDistance = 0;

        for (int i = 1; i < V; i++) {
            if (parent[i] != -1) {
                System.out.println(
                        departments[parent[i]].name + " - " +
                                departments[i].name + " : " +
                                graph[i][parent[i]] + " meters");
                totalDistance += graph[i][parent[i]];
            }
        }

        System.out.println("\nTotal cable length: " + totalDistance + " meters");
    }

    static void findMST(int[][] graph, int V, Department[] departments, int start) {
        int[] parent = new int[100];
        int[] key = new int[100];
        boolean[] mstSet = new boolean[100];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
            parent[i] = -1;
        }

        key[start] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = findMinKey(key, mstSet, V);

            if (u == -1)
                break;

            mstSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        printMST(parent, graph, departments, V);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of departments: ");
        int V = sc.nextInt();
        sc.nextLine(); // consume newline

        Department[] departments = new Department[100];
        int[][] graph = new int[100][100];

        System.out.println("\nEnter department details:");
        for (int i = 0; i < V; i++) {
            System.out.print("Department " + i + " name: ");
            String name = sc.nextLine();

            System.out.print("Location: ");
            String location = sc.nextLine();

            departments[i] = new Department(name, location);
        }

        System.out.println("\nEnter distances between departments:");
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                System.out.print(departments[i].name + " to " + departments[j].name + ": ");
                graph[i][j] = sc.nextInt();
                graph[j][i] = graph[i][j];
            }
        }

        while (true) {
            System.out.println("\nAvailable departments:");
            for (int i = 0; i < V; i++) {
                System.out.println(i + ": " + departments[i].name);
            }

            System.out.print("Enter starting department index (0-" + (V - 1) + "): ");
            int start = sc.nextInt();

            findMST(graph, V, departments, start);

            System.out.print("Try another starting point? (y/n): ");
            char again = sc.next().charAt(0);

            if (again != 'y' && again != 'Y')
                break;
        }

        sc.close();
    }
}