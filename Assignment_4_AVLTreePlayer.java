import java.util.Scanner;

class Node {
    int player_id;
    int score;
    int height;
    Node left, right;

    Node(int id, int sc) {
        this.player_id = id;
        this.score = sc;
        this.height = 1;
    }
}

class AVLTree {
    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private Node insert(Node node, int id, int sc) {
        if (node == null)
            return new Node(id, sc);

        if (sc < node.score || (sc == node.score && id < node.player_id)) {
            node.left = insert(node.left, id, sc);
        } else if (sc > node.score || (sc == node.score && id > node.player_id)) {
            node.right = insert(node.right, id, sc);
        } else {
            System.out.println("Duplicate Entry");
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bf = balanceFactor(node);

        // LL
        if (bf > 1 && (sc < node.left.score || (sc == node.left.score && id < node.left.player_id)))
            return rightRotate(node);

        // RR
        if (bf < -1 && (sc > node.right.score || (sc == node.right.score && id > node.right.player_id)))
            return leftRotate(node);

        // LR
        if (bf > 1 && (sc > node.left.score || (sc == node.left.score && id > node.left.player_id))) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (bf < -1 && (sc < node.right.score || (sc == node.right.score && id < node.right.player_id))) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minValueNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node delete(Node node, int id, int sc) {
        if (node == null)
            return null;

        if (sc < node.score || (sc == node.score && id < node.player_id)) {
            node.left = delete(node.left, id, sc);
        } else if (sc > node.score || (sc == node.score && id > node.player_id)) {
            node.right = delete(node.right, id, sc);
        } else {
            // found

            if (node.left == null && node.right == null)
                return null;

            if (node.left == null)
                return node.right;

            if (node.right == null)
                return node.left;

            Node temp = minValueNode(node.right);
            node.player_id = temp.player_id;
            node.score = temp.score;

            node.right = delete(node.right, temp.player_id, temp.score);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bf = balanceFactor(node);

        // LL
        if (bf > 1 && balanceFactor(node.left) >= 0)
            return rightRotate(node);

        // LR
        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR
        if (bf < -1 && balanceFactor(node.right) <= 0)
            return leftRotate(node);

        // RL
        if (bf < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void registerPlayer(int id, int sc) {
        root = insert(root, id, sc);
    }

    public void removePlayer(int id, int sc) {
        root = delete(root, id, sc);
    }

    private void display(Node node) {
        if (node == null)
            return;

        display(node.right);
        System.out.println("Player ID: " + node.player_id + " Score: " + node.score);
        display(node.left);
    }

    public void display() {
        if (root == null) {
            System.out.println("No Players Available");
            return;
        }
        display(root);
    }
}

public class Assignment_4_AVLTreePlayer {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Insert Player");
            System.out.println("2. Display Leaderboard");
            System.out.println("3. Remove Player");
            System.out.println("4. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Score: ");
                    int scs = sc.nextInt();
                    tree.registerPlayer(id, scs);
                    break;

                case 2:
                    tree.display();
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int rid = sc.nextInt();
                    System.out.print("Enter Score: ");
                    int rsc = sc.nextInt();
                    tree.removePlayer(rid, rsc);
                    break;

                case 4:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}