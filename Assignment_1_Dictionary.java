import java.util.*;

class Node {
  String key, value;
  Node left, right;

  Node(String k, String v) {
    key = k;
    value = v;
    left = right = null;
  }
}

class DictBST {

  Node root;

  public void insert(String key, String value) {

    Node newNode = new Node(key, value);

    if (root == null) {
      root = newNode;
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
      parent.left = newNode;
    else
      parent.right = newNode;
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

  public void delete(String key) {

    Node parent = null;
    Node current = root;

    while (current != null && !current.key.equals(key)) {

      parent = current;

      if (key.compareTo(current.key) < 0)
        current = current.left;
      else
        current = current.right;
    }

    if (current == null) {
      System.out.println("Word not found.");
      return;
    }

    if (current.left == null || current.right == null) {

      Node child;

      if (current.left != null)
        child = current.left;
      else
        child = current.right;

      if (parent == null)
        root = child;
      else if (parent.left == current)
        parent.left = child;
      else
        parent.right = child;
    } else {

      Node succParent = current;
      Node successor = current.right;

      while (successor.left != null) {
        succParent = successor;
        successor = successor.left;
      }

      current.key = successor.key;
      current.value = successor.value;

      if (succParent.left == successor)
        succParent.left = successor.right;
      else
        succParent.right = successor.right;
    }

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

public class Assignment_1_Dictionary {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    DictBST dict = new DictBST();
    DictBST copyDict = null;

    int choice;

    do {
      System.out.println("\n--- Dictionary Menu ---");
      System.out.println("1.Insert");
      System.out.println("2.Delete");
      System.out.println("3.Search");
      System.out.println("4.Display");
      System.out.println("5.Mirror");
      System.out.println("6.Copy");
      System.out.println("7.Level Wise");
      System.out.println("8.Exit");

      choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {

        case 1:
          System.out.print("Word: ");
          String w = sc.nextLine();
          System.out.print("Meaning: ");
          String m = sc.nextLine();
          dict.insert(w, m);
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
          copyDict = dict.copy();
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