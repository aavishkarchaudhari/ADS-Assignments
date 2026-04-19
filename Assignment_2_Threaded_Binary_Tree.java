import java.util.Scanner;

class Node {
    int data;
    int lth, rth;
    Node left, right;

    Node(int data) {
        this.data = data;
        lth = rth = 1;
    }
}

class TBT {

    Node insert(Node root, int key) {

        Node temp = new Node(key);
        Node parent = null;

        if (root == null) {
            root = temp;
        } else {
            Node curr = root;

            while (curr != null) {

                if (key == curr.data) {
                    System.out.println("Duplicate key not allowed");
                    return root;
                }

                parent = curr;

                if (key < curr.data) {
                    if (curr.lth == 0)
                        curr = curr.left;
                    else
                        break;
                } else {
                    if (curr.rth == 0)
                        curr = curr.right;
                    else
                        break;
                }
            }

            if (key < parent.data) {

                temp.left = parent.left;
                temp.right = parent;

                parent.lth = 0;
                parent.left = temp;

            } else {

                temp.left = parent;
                temp.right = parent.right;

                parent.rth = 0;
                parent.right = temp;
            }
        }

        return root;
    }

    Node leftMost(Node root) {
        while (root.lth == 0)
            root = root.left;
        return root;
    }

    void inorder(Node root) {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        Node curr = leftMost(root);

        while (curr != null) {
            System.out.print(curr.data + " ");

            if (curr.rth == 1) {
                curr = curr.right;
            } else {
                curr = leftMost(curr.right);
            }
        }
        System.out.println();
    }
}

public class Assignment_2_Threaded_Binary_Tree {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TBT tbt = new TBT();
        Node root = null;

        int choice, value;

        do {
            System.out.println("\n1. Insert");
            System.out.println("2. Inorder Traversal");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter value: ");
                    value = sc.nextInt();
                    root = tbt.insert(root, value);
                    break;

                case 2:
                    System.out.print("Inorder: ");
                    tbt.inorder(root);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 3);

        sc.close();
    }
}