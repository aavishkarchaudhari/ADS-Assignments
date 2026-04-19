import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Node {
    String key, value;
    Node left, right;

    Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

class DictBST {

    Node root;

    public void insert(String key, String value) {
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;

            int cmp = key.compareTo(current.key);

            if (cmp == 0) {
                current.value = value;
                System.out.println("Duplicate key updated.");
                return;
            } else if (cmp < 0)
                current = current.left;
            else
                current = current.right;
        }

        if (key.compareTo(parent.key) < 0)
            parent.left = new Node(key, value);
        else
            parent.right = new Node(key, value);
    }

    public void search(String key) {
        Node current = root;

        while (current != null) {

            int cmp = key.compareTo(current.key);

            if (cmp == 0) {
                System.out.println("Meaning: " + current.value);
                return;
            } else if (cmp < 0)
                current = current.left;
            else
                current = current.right;
        }

        System.out.println("Word not found.");
    }

    private Node getSuccessor(Node curr) {
        curr = curr.right;
        while (curr != null && curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    private Node delNode(Node root, String key) {
        if (root == null)
            return root;

        int cmp = root.key.compareTo(key);

        if (cmp > 0) {
            root.left = delNode(root.left, key);
        } else if (cmp < 0) {
            root.right = delNode(root.right, key);
        } else {
            // Node with 0 or 1 child
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;

            // Node with 2 children
            Node succ = getSuccessor(root);
            root.key = succ.key;
            root.value = succ.value;
            root.right = delNode(root.right, succ.key);
        }
        return root;
    }

    public void delete(String key) {
        root = delNode(root, key);
        System.out.println("Deleted successfully.");
    }

    public void display() {
        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            System.out.println(current.key + " : " + current.value);

            current = current.right;
        }
    }

    public void mirror() {
        if (root == null)
            return;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node temp = q.poll();

            Node t = temp.left;
            temp.left = temp.right;
            temp.right = t;

            if (temp.left != null)
                q.add(temp.left);
            if (temp.right != null)
                q.add(temp.right);
        }
    }

    public DictBST copy() {
        DictBST newTree = new DictBST();

        if (root == null)
            return newTree;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node temp = q.poll();

            newTree.insert(temp.key, temp.value);

            if (temp.left != null)
                q.add(temp.left);
            if (temp.right != null)
                q.add(temp.right);
        }

        return newTree;
    }

    public void displayLevelWise() {
        if (root == null)
            return;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node temp = q.poll();
            System.out.println(temp.key + " : " + temp.value);

            if (temp.left != null)
                q.add(temp.left);
            if (temp.right != null)
                q.add(temp.right);
        }
    }
}

public class Assignment_1_Dictionary_BST {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DictBST dict = new DictBST();

        int choice;

        do {
            System.out.println(
                    "\n--- Dictionary Menu ---\n1.Insert\n2.Delete\n3.Search\n4.Display\n5.Mirror\n6.Copy\n7.Level Wise\n8.Exit");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Word: ");
                    String w = sc.nextLine();

                    System.out.print("Meaning: ");
                    dict.insert(w, sc.nextLine());
                    break;

                case 2:
                    System.out.print("Delete word: ");
                    dict.delete(sc.nextLine());
                    break;

                case 3:
                    System.out.print("Search word: ");
                    dict.search(sc.nextLine());
                    break;

                case 4:
                    dict.display();
                    break;

                case 5:
                    dict.mirror();
                    System.out.println("Mirrored.");
                    break;

                case 6:
                    dict.copy();
                    System.out.println("Copied.");
                    break;

                case 7:
                    dict.displayLevelWise();
                    break;
            }

        } while (choice != 8);

        sc.close();
    }
}