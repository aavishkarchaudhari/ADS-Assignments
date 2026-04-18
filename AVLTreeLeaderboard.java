import java.util.Scanner;

class Node {
  int player_id;
  int score;
  Node left, right;
  int h;

  Node(int id, int sc) {
    player_id = id;
    score = sc;
    h = 0;
  }
}

class Tree {
  private Node root = null;

  public int height(Node node) {
    if (node == null)
      return -1;
    else {
      int l = height(node.left);
      int r = height(node.right);
      return Math.max(l, r) + 1;
    }
  }

  public int balanceFactor(Node node) {
    if (node == null)
      return -1;
    else {
      return height(node.left) - height(node.right);
    }
  }

  private Node rightRotate(Node y) {
    Node x = y.left;
    Node t = x.right;
    x.right = y;
    y.left = t;

    y.h = 1 + Math.max(height(y.left), height(y.right));
    x.h = 1 + Math.max(height(x.left), height(x.right));
    return x;
  }

  private Node leftRotate(Node y) {
    Node x = y.right;
    Node t = x.left;
    x.left = y;
    y.right = t;

    y.h = 1 + Math.max(height(y.left), height(y.right));
    x.h = 1 + Math.max(height(x.left), height(x.right));
    return x;
  }

  private Node insert(Node node, int id, int sc) {
    if (node == null)
      return new Node(id, sc);

    if (sc < node.score || (sc == node.score && id < node.player_id))
      node.left = insert(node.left, id, sc);
    else if (sc > node.score || (sc == node.score && id > node.player_id))
      node.right = insert(node.right, id, sc);
    else {
      System.out.println("Duplicate Entry");
      return node;
    }

    node.h = height(node);
    int bf = balanceFactor(node);

    // LL Case
    if (bf > 1 && (sc < node.left.score || (sc == node.left.score && id < node.left.player_id)))
      return rightRotate(node);

    // RR Case
    if (bf < -1 && (sc > node.right.score || (sc == node.right.score && id > node.right.player_id)))
      return leftRotate(node);

    // LR Case
    if (bf > 1 && (sc > node.left.score || (sc == node.left.score && id > node.left.player_id))) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    // RL Case
    if (bf < -1 && (sc < node.right.score || (sc == node.right.score && id < node.right.player_id))) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }

  public void registerPlayer(int id, int sc) {
    root = insert(root, id, sc);
  }

  private Node minValueNode(Node node) {
    Node current = node;
    while (current.left != null)
      current = current.left;
    return current;
  }

  private Node deleteNode(Node node, int id, int sc) {
    if (node == null)
      return node;

    if (sc < node.score || (sc == node.score && id < node.player_id))
      node.left = deleteNode(node.left, id, sc);
    else if (sc > node.score || (sc == node.score && id > node.player_id))
      node.right = deleteNode(node.right, id, sc);
    else {
      if (node.left == null || node.right == null) {
        node = (node.left == null) ? node.right : node.left;
      } else {
        Node temp = minValueNode(node.right);
        node.player_id = temp.player_id;
        node.score = temp.score;
        node.right = deleteNode(node.right, temp.player_id, temp.score);
      }
    }

    if (node == null)
      return node;

    node.h = height(node);
    int bf = balanceFactor(node);

    // Balancing after deletion
    if (bf > 1 && balanceFactor(node.left) >= 0)
      return rightRotate(node);

    if (bf > 1 && balanceFactor(node.left) < 0) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    if (bf < -1 && balanceFactor(node.right) <= 0)
      return leftRotate(node);

    if (bf < -1 && balanceFactor(node.right) > 0) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }

  private Node search(Node node, int id) {
    if (node == null)
      return null;
    if (node.player_id == id)
      return node;

    Node found = search(node.left, id);
    if (found != null)
      return found;

    return search(node.right, id);
  }

  public void removePlayer(int id) {
    Node temp = search(root, id);
    if (temp == null) {
      System.out.println("Player Not Found");
      return;
    }
    root = deleteNode(root, temp.player_id, temp.score);
  }

  private void display(Node node) {
    if (node != null) {
      display(node.right);
      System.out.println("Player ID: " + node.player_id + " Score: " + node.score);
      display(node.left);
    }
  }

  public void display() {
    if (root == null)
      System.out.println("No Players Available");
    else
      display(root);
  }
}

public class AVLTreeLeaderboard {
  public static void main(String[] args) {
    Tree t = new Tree();
    Scanner sc = new Scanner(System.in);
    int choice, id, score;

    while (true) {
      System.out.println("\n1. Player Registration");
      System.out.println("2. Leaderboard Display");
      System.out.println("3. Remove Player");
      System.out.println("4. Exit");
      System.out.print("Enter Choice: ");

      choice = sc.nextInt();

      switch (choice) {
        case 1:
          System.out.print("Enter Player ID: ");
          id = sc.nextInt();
          System.out.print("Enter Score: ");
          score = sc.nextInt();
          t.registerPlayer(id, score);
          break;
        case 2:
          System.out.println("\n--- Leaderboard ---");
          t.display();
          break;
        case 3:
          System.out.print("Enter Player ID to Remove: ");
          id = sc.nextInt();
          t.removePlayer(id);
          break;
        case 4:
          sc.close();
          System.exit(0);
        default:
          System.out.println("Invalid Choice");
      }
    }
  }
}