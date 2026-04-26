import java.util.Scanner;

class Node {
    int data;
    int height;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.height = 1;
    }
}

public class AVLTree {
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

    private Node insert(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        } else {
            System.out.println("Duplicate data not allowed");
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bf = balanceFactor(node);

        if (bf > 1 && data < node.left.data) {
            return rightRotate(node);
        }

        if (bf < -1 && data > node.right.data) {
            return leftRotate(node);
        }

        if (bf > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (bf < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node successor(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node delete(Node node, int data) {
        if (node == null) {
            return null;
        }

        if (data < node.data) {
            node.left = delete(node.left, data);
        } else if (data > node.data) {
            node.right = delete(node.right, data);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node temp = successor(node.right);
                node.data = temp.data;
                node.right = delete(node.right, temp.data);
            }
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bf = balanceFactor(node);

        if (bf > 1 && balanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (bf < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        if (bf < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    public void insert(int data) {
        root = insert(root, data);
    }

    public void delete(int data) {
        root = delete(root, data);
    }

    public void display() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        inOrder(root);
        System.out.println();
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Scanner sc = new Scanner(System.in);

        int choice;
        int data;

        while (true) {
            System.out.println("\n1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Display");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter data to insert: ");
                    data = sc.nextInt();
                    tree.insert(data);
                    break;

                case 2:
                    System.out.print("Enter data to delete: ");
                    data = sc.nextInt();
                    tree.delete(data);
                    break;

                case 3:
                    tree.display();
                    break;

                case 4:
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}