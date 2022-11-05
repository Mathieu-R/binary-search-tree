/**
 * Implementation of a Binary Search Tree
 */
public class BST<Key extends Comparable<Key>, Value> {
  public BST() {

  }

  /**
   * 2-Node class helper
   */
  public class Node {

    private Key key;
    private Value value;

    private Node left;
    private Node right;

    private int size;

    public Node(Key key, Value value) {
      this.key = key;
      this.value = value;

      this.size = 1;
    }

    public Node(Key key, Value value, Node leftChild, Node rightChild) {
      this.key = key;
      this.value = value;
      this.left = leftChild;
      this.right = rightChild;

      this.size = this.left.size() + this.right.size() + 1;
    }

    public int size() {
      return this.size;
    }
  }
}
