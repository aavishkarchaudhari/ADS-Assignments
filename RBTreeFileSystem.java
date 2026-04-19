import java.util.Scanner;

public class RBTreeFileSystem {

    static final boolean RED = true;
    static final boolean BLACK = false;

    static class Node {
        String data;
        boolean color;
        Node left, right, parent;

        Node(String val) {
            data = val;
            color = RED;
        }
    }

    private Node root;

    public RBTreeFileSystem() {
        root = null;
    }

    private Node getSibling(Node n) {
        if (n.parent == null)
            return null;
        if (n == n.parent.left)
            return n.parent.right;
        return n.parent.left;
    }

    private boolean hasRedChild(Node n) {
        return (n.left != null && n.left.color == RED) ||
                (n.right != null && n.right.color == RED);
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;

        if (y.right != null)
            y.right.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.right = x;
        x.parent = y;
    }

    private void fixInsert(Node x) {
        while (x != root && x.parent.color == RED) {
            Node parent = x.parent;
            Node grand = parent.parent;

            if (parent == grand.left) {
                Node uncle = grand.right;

                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grand.color = RED;
                    x = grand;
                } else {
                    if (x == parent.right) {
                        rotateLeft(parent);
                        x = parent;
                        parent = x.parent;
                    }
                    rotateRight(grand);
                    boolean tmp = parent.color;
                    parent.color = grand.color;
                    grand.color = tmp;
                    x = parent;
                }
            } else {
                Node uncle = grand.left;

                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grand.color = RED;
                    x = grand;
                } else {
                    if (x == parent.left) {
                        rotateRight(parent);
                        x = parent;
                        parent = x.parent;
                    }
                    rotateLeft(grand);
                    boolean tmp = parent.color;
                    parent.color = grand.color;
                    grand.color = tmp;
                    x = parent;
                }
            }
        }
        root.color = BLACK;
    }

    private Node successor(Node x) {
        while (x.left != null)
            x = x.left;
        return x;
    }

    private Node BSTreplace(Node x) {
        if (x.left != null && x.right != null)
            return successor(x.right);
        if (x.left == null && x.right == null)
            return null;
        return (x.left != null) ? x.left : x.right;
    }

    private void fixDoubleBlack(Node x) {
        if (x == root)
            return;

        Node sibling = getSibling(x);
        Node parent = x.parent;

        if (sibling == null) {
            fixDoubleBlack(parent);
        } else {
            if (sibling.color == RED) {
                parent.color = RED;
                sibling.color = BLACK;

                if (sibling == parent.left)
                    rotateRight(parent);
                else
                    rotateLeft(parent);

                fixDoubleBlack(x);
            } else {
                if (hasRedChild(sibling)) {
                    if (sibling.left != null && sibling.left.color == RED) {
                        if (sibling == parent.left) {
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rotateRight(parent);
                        } else {
                            sibling.left.color = parent.color;
                            rotateRight(sibling);
                            rotateLeft(parent);
                        }
                    } else {
                        if (sibling == parent.left) {
                            sibling.right.color = parent.color;
                            rotateLeft(sibling);
                            rotateRight(parent);
                        } else {
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            rotateLeft(parent);
                        }
                    }
                    parent.color = BLACK;
                } else {
                    sibling.color = RED;
                    if (parent.color == BLACK)
                        fixDoubleBlack(parent);
                    else
                        parent.color = BLACK;
                }
            }
        }
    }

    private void deleteNode(Node v) {
        Node u = BSTreplace(v);
        boolean uvBlack = ((u == null || u.color == BLACK) && (v.color == BLACK));
        Node parent = v.parent;

        if (u == null) {
            if (v == root) {
                root = null;
            } else {
                if (uvBlack) {
                    fixDoubleBlack(v);
                }

                if (v == parent.left)
                    parent.left = null;
                else
                    parent.right = null;
            }
            return;
        }

        if (v.left == null || v.right == null) {
            if (v == root) {
                v.data = u.data;
                v.left = v.right = null;
            } else {
                if (v == parent.left)
                    parent.left = u;
                else
                    parent.right = u;

                u.parent = parent;

                if (uvBlack)
                    fixDoubleBlack(u);
                else
                    u.color = BLACK;
            }
            return;
        }

        String tmp = u.data;
        u.data = v.data;
        v.data = tmp;
        deleteNode(u);
    }

    private Node search(Node root, String key) {
        if (root == null || root.data.equals(key))
            return root;
        if (key.compareTo(root.data) < 0)
            return search(root.left, key);
        return search(root.right, key);
    }

    private void inorder(Node root) {
        if (root == null)
            return;
        inorder(root.left);
        String nodeColor = (root.color == RED) ? "RED" : "BLACK";
        System.out.println(" |-- " + root.data + " (" + nodeColor + ")");
        inorder(root.right);
    }

    public void insert(String data) {
        Node newNode = new Node(data);
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;

            if (data.equals(x.data)) {
                System.out.println("Directory '" + data + "' already exists!");
                return;
            }

            if (data.compareTo(x.data) < 0)
                x = x.left;
            else
                x = x.right;
        }

        newNode.parent = y;

        if (y == null)
            root = newNode;
        else if (data.compareTo(y.data) < 0)
            y.left = newNode;
        else
            y.right = newNode;

        fixInsert(newNode);
        System.out.println("Directory '" + data + "' added successfully.");
    }

    public void deleteValue(String data) {
        Node v = search(root, data);
        if (v == null) {
            System.out.println("Directory not found.");
            return;
        }
        deleteNode(v);
        System.out.println("Directory '" + data + "' deleted successfully.");
    }

    public void searchDir(String key) {
        if (search(root, key) != null)
            System.out.println("Directory '" + key + "' Found.");
        else
            System.out.println("Directory '" + key + "' Not Found.");
    }

    public void display() {
        if (root == null) {
            System.out.println("Empty Directory System.");
            return;
        }
        System.out.println("\nCurrent Directories (In-order)+");
        inorder(root);
    }

    public static void main(String[] args) {
        RBTreeFileSystem fs = new RBTreeFileSystem();
        Scanner sc = new Scanner(System.in);
        int choice;
        String name;

        do {
            System.out.println(" 1. Add Directory");
            System.out.println(" 2. Delete Directory");
            System.out.println(" 3. Search Directory");
            System.out.println(" 4. Display All Directories");
            System.out.println(" 5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter directory name: ");
                    name = sc.next();
                    fs.insert(name);
                    break;

                case 2:
                    System.out.print("Enter directory to delete: ");
                    name = sc.next();
                    fs.deleteValue(name);
                    break;

                case 3:
                    System.out.print("Enter directory to search: ");
                    name = sc.next();
                    fs.searchDir(name);
                    break;

                case 4:
                    fs.display();
                    break;

                case 5:
                    System.out.println("Exiting File System...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}