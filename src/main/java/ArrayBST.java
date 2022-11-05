import java.util.ArrayList;


/**
 *  We study a BST representation using an arrayList internal representation.
 *  Rather than using a Linked-Node Data-Structure, the left/right children
 *  will be encoded with indices in array lists.
 *  More exactly, in this data-structure each node
 *  is represented by an index i (that correspond to the ith added element)
 *  The left  node of node i, if any, is located
 *        at index idxLeftNode.get(i) otherwise idxLeftNode.get(i) == NONE
 *  The right node of node i, if any is located
 *       at index idxRightNode.get(i) otherwise idxRightNode.get(i) == NONE
 *
 *  The tree below is the result of putting (key,value) pairs (12,A),(15,B),(5,C),(8,d),(1,E) (in this order)
 *
 *                12(A)
 *                / \
 *               /   \
 *             5(C)  15(B)
 *             / \
 *          1(E)  8(D)
 *
 *   The state of internal array list after those put operations is
 *    values:        A, B, C, D, E
 *    keys:        12, 15, 5, 8, 1
 *    idxLeftNode:  2, -1, 4,-1,-1
 *    idxRightNode: 1, -1, 3,-1,-1
 *
 *  You can implement the get method before the put method if you prefer since
 *  the two methods will be graded separately.
 *
 *  You cannot add of change the fields already declared, nor change
 *  the signatures of the methods in this
 *  class but feel free to add methods if needed.
 *
 */
public class ArrayBST<Key extends Comparable<Key>, Value> {

    // The next four array lists should always have exactly equal size after a put

    public ArrayList<Key> keys;
    public ArrayList<Value> values;

    public ArrayList<Integer> idxLeftNode; // idxLeftNode[i] = index of left node of i
    public ArrayList<Integer> idxRightNode; // idxRightNode[i] = index of right node of i

    final int NONE = -1;

    public ArrayBST() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
        idxLeftNode = new ArrayList<>();
        idxRightNode = new ArrayList<>();
    }

    /**
     * Insert the entry in the BST, replace the value if the key is already present
     * @param key a key that is present or not in the BST
     * @param val the value that must be attached to this key
     * @return true if the key was added, false if already present and the value has simply been erased
     */
    public boolean put(Key key, Value val) {
        int rootIndex = 0;

        // base case: BST is empty
        if (keys.size() == 0) {
            createNode(key, val, NONE, NONE);
            return true;
        }

        while (rootIndex != NONE) {
            int cmp = key.compareTo(keys.get(rootIndex));
            if (cmp < 0) {
                int indexOfKeyAtLeft = idxLeftNode.get(rootIndex);

                // Null link. Create a new node.
                if (indexOfKeyAtLeft == NONE) {
                    createNode(key, val, NONE, NONE);

                    // update parent
                    idxLeftNode.set(rootIndex, keys.size() - 1);
                }

                rootIndex = indexOfKeyAtLeft;
            } else if (cmp > 0) {
                int indexOfKeyAtRight = idxRightNode.get(rootIndex);

                // Null link. Create a new node.
                if (indexOfKeyAtRight == NONE) {
                    createNode(key, val, NONE, NONE);

                    // update parent
                    idxRightNode.set(rootIndex, keys.size() - 1);
                }

                rootIndex = indexOfKeyAtRight;
            } else {
                // key found
                updateNode(rootIndex, val);
                return false;
            }
        }


        return true;
    }

    public void createNode(Key key, Value val, int idxRightChild, int idxLeftChild) {
        keys.add(key);
        values.add(val);
        idxLeftNode.add(idxLeftChild);
        idxRightNode.add(idxRightChild);
    }

    public void updateNode(int idx, Value val) {
        values.set(idx, val);
    }

    /**
     * Return the value attached to this key, null if the key is not present
     * @param key
     * @return the value attached to this key, null if the key is not present
     */
    public Value get(Key key) {
        int rootIndex = 0;

        // base case: BST is empty
        if (keys.size() == 0) {
            return null;
        }

        while (rootIndex != NONE) {
            int cmp = key.compareTo(keys.get(rootIndex));
            if (cmp < 0) {
                int indexOfKeyAtLeft = idxLeftNode.get(rootIndex);
                rootIndex = indexOfKeyAtLeft;
            } else if (cmp > 0) {
                int indexOfKeyAtRight = idxRightNode.get(rootIndex);
                rootIndex = indexOfKeyAtRight;
            } else {
                // key found
                return values.get(rootIndex);
            }
        }

        return null;
    }
}
