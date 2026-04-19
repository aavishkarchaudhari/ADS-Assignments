import java.util.Scanner;

class Junction {
    int id;
    int traffic;
    Junction left, right;

    boolean lThread, rThread;

    int height;

    Junction(int id, int traffic) {
        this.id = id;
        this.traffic = traffic;
        this.left = this.right = null;
        this.height = 1;
        lThread = rThread = false;
    }
}

class TrafficSystem {

    Junction root;

    int height(Junction n) {
        return (n == null) ? 0 : n.height;
    }

    int getBalance(Junction n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Junction rightRotate(Junction y) {
        Junction x = y.left;
        Junction T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Junction leftRotate(Junction x) {
        Junction y = x.right;
        Junction T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Junction insert(Junction node, int id, int traffic) {
        if (node == null)
            return new Junction(id, traffic);

        if (id < node.id)
            node.left = insert(node.left, id, traffic);
        else if (id > node.id)
            node.right = insert(node.right, id, traffic);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && id < node.left.id)
            return rightRotate(node);

        if (balance < -1 && id > node.right.id)
            return leftRotate(node);

        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Junction search(Junction root, int id) {
        if (root == null || root.id == id)
            return root;

        if (id < root.id)
            return search(root.left, id);

        return search(root.right, id);
    }

    void updateTraffic(int id, int newTraffic) {
        Junction node = search(root, id);
        if (node != null) {
            node.traffic = newTraffic;
        } else {
            System.out.println("Junction not found");
        }
    }

    void inorderTraversal(Junction root) {
        if (root == null)
            return;

        java.util.Stack<Junction> stack = new java.util.Stack<>();
        Junction curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            System.out.println("Junction: " + curr.id + " Traffic: " + curr.traffic);

            curr = curr.right;
        }
    }

    void findLeastTrafficPath() {
        java.util.List<Junction> list = new java.util.ArrayList<>();
        storeInorder(root, list);

        list.sort((a, b) -> a.traffic - b.traffic);

        System.out.println("Best junctions (least traffic):");
        for (int i = 0; i < Math.min(3, list.size()); i++) {
            System.out.println("Junction " + list.get(i).id +
                    " Traffic: " + list.get(i).traffic);
        }
    }

    void storeInorder(Junction node, java.util.List<Junction> list) {
        if (node == null)
            return;
        storeInorder(node.left, list);
        list.add(node);
        storeInorder(node.right, list);
    }
}

public class SmartTrafficSystem {
    public static void main(String[] args) {
        TrafficSystem system = new TrafficSystem();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("1. Insert Junction");
            System.out.println("2. Search Junction");
            System.out.println("3. Update Traffic");
            System.out.println("4. Display All Junctions");
            System.out.println("5. Suggest Best Path");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Junction ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Traffic Density: ");
                    int traffic = sc.nextInt();

                    system.root = system.insert(system.root, id, traffic);
                    System.out.println("Junction inserted successfully.");
                    break;

                case 2:
                    System.out.print("Enter Junction ID to search: ");
                    int searchId = sc.nextInt();

                    Junction result = system.search(system.root, searchId);

                    if (result != null) {
                        System.out.println("Found -> ID: " + result.id +
                                " Traffic: " + result.traffic);
                    } else {
                        System.out.println("Junction not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Junction ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Traffic: ");
                    int newTraffic = sc.nextInt();

                    system.updateTraffic(updateId, newTraffic);
                    break;

                case 4:
                    System.out.println("All Junctions (Inorder Traversal):");
                    system.inorderTraversal(system.root);
                    break;

                case 5:
                    system.findLeastTrafficPath();
                    break;

                case 6:
                    System.out.println("Exiting system...");
                    sc.close();
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

    }
}