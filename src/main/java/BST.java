import java.util.NoSuchElementException;

/**
 * Implementation of a Binary Search Tree
 */
public class BST<Key extends Comparable<Key>, Value> {
	private Node root;

	public BST() {
	}

	/**
	 * 2-Node class helper
	 */
	public class Node {

		private Key key;
		private Value val;

		private Node left;
		private Node right;

		private int size;

		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;

			this.size = 1;
		}

		public Node(Key key, Value val, Node leftChild, Node rightChild) {
			this.key = key;
			this.val = val;
			this.left = leftChild;
			this.right = rightChild;

			this.size = this.left.size() + this.right.size() + 1;
		}

		public int size() {
			return this.size;
		}
	}

	public Value get(Key key) {
		return get(root, key);
	}

	public Value get(Node x, Key key) {
		// base case: no root (for the subtree)
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);

		// perfect match
		if (cmp == 0) {
			return x.val;
		}

		if (cmp < 0) {
			return get(x.left, key);
		} else {
			return get(x.right, key);
		}
	}

	public Node put(Key key, Value val) {
		return put(root, key, val);
	}

	public Node put(Node x, Key key, Value val) {
		// base case: no root (for the subtree)
		// create a new node
		if (x == null) {
			return new Node(key, val);
		}

		int cmp = key.compareTo(x.key);

		// note: do not directly return the node since we have to update the counter after it

		// perfect match => update value of the node
		if (cmp == 0) {
			x.val = val;
		}

		if (cmp < 0) {
			x.left = put(x.left, key, val);
		} else {
			x.right = put(x.right, key, val);
		}

		// update the node counter
		// (because if the key does not exist, we create a new node so size of the whole Tree would increase of 1)
		x.size = x.left.size() + x.right.size() + 1;
		return x;
	}

	public void deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		root = deleteMin(root);
	}

	public Node deleteMin(Node x) {
		// base case: nothing at left => min is the root node => return right (equivalent to delete the root node)
		if (x.left == null) {
			return x.right;
		}

		// look for the min by going at left recursively
		x.left = deleteMin(x.left);

		// update root node size
		x.size = x.left.size() + x.right.size() + 1;
		return x;
	}

	public void delete(Key key) {
		root = delete(root, key);
	}

	public Node delete(Node x, Key key) {
		// base case: no root (for the subtree)
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);

		// perfect match => key to delete found
		// 3 cases
		if (cmp == 0) {
			// case 1: no children => delete root
			if (x.left == null && x.right == null) {
				return null;
			}

			// case 2: 1 child
			if (x.left == null) {
				// no left child
				// right child becomes root
				x = x.right;
			} else if (x.right == null) {
				// no right child
				// left child becomes root
				x = x.left;
			}

			// case 3: 2 children
			if (x.left != null && x.right != null) {
				// find the minimum at right
				// to keep the invariant everything at left is smaller than root and everything at right is greater than root
				Node minimumAtRight = min(x.right);

				// minimum at right becomes the root
				Node temp = x;
				x = minimumAtRight;

				// delete minimum at right (duplicate) and rewrite left and right child for the new root
				x.right = deleteMin(temp.right);
				x.left = temp.left;
			}
		}

		// key < root key => REWRITE at left recursively
		if (cmp < 0) {
			x.left = delete(x.left, key);
		} else {
			// key > root key => REWRITE at right recursively
			x.right = delete(x.right, key);
		}

		// for any case: rewrite root size
		x.size = x.left.size() + x.right.size() + 1;

		return x;
	}

	public Node min() {
		return min(root);
	}

	/*
	return the node which key is the minimum of the subtree x
	 */
	public Node min(Node x) {
		// base case: no root (for the subtree)
		if (x == null) {
			throw new NoSuchElementException("(Sub)tree is empty so no minimum value.");
		}

		// no left child => min is the root
		if (x.left == null) {
			return x;
		} else {
			// otherwise, continue searching at left recursively
			return min(x.left);
		}
	}

	public Node max() {
		return max(root);
	}

	public Node max(Node x) {
		// base case: no root (for the subtree)
		if (x == null) {
			throw new NoSuchElementException("(Sub)tree is empty so no minimum value.");
		}

		// no right child => max is the root
		if (x.right == null) {
			return x;
		} else {
			// otherwise, continue searching at right recursively
			return max(x.right);
		}
	}

	public Key floor(Node x, Key key) {
		// base case: no root (for the subtree)
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);

		// perfect match
		if (cmp == 0) {
			return x.key;
		}

		// key < root key => search left recursively
		if (cmp < 0) {
			return floor(x.left, key);
		} else {
			// key > root key => investigate at right

			// if no right child
			if (x.right == null) {
				// floor is the root key
				return x.key;
			} else {
				// search right recursively
				return floor(x.right, key);
			}
		}
	}

	public Key ceil(Node x, Key key) {
		// base case: no root (for the subtree)
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);

		// perfect match
		if (cmp == 0) {
			return x.key;
		}

		// key < root key => investigate at left
		if (cmp < 0) {
			// if no left child
			if (x.left == null) {
				// ceil found
				return x.key;
			} else {
				// search left recursively
				return ceil(x.left, key);
			}
		} else {
			// key > root key => search right recursively
			return ceil(x.right, key);
		}
	}

	public Key select(int k) {
		// base case: key overflow
		if (k < 0 || k > root.size() - 1) {
			throw new IllegalArgumentException("Key should be in the range [0, Tree size - 1]");
		}

		return select(root, k);
	}

	/*
	return the key just higher than the k-smallest keys in the Tree
	 */
	public Key select(Node x, int k) {
		// base case: no root (for the subtree)
		if (x == null) {
			return null;
		}

		// perfect match => key just higher than the (size of the Tree)-smallest keys is the root
		if (k == x.size()) {
			return x.key;
		}

		// note: left size of the Tree contains the k-first keys
		if (k < x.size()) {
			// search left recursively
			return select(x.left, k);
		} else {
			// search right recursively + (remove left size + root size)
			return select(x.right, k - x.left.size - 1);
		}
	}

	public boolean isEmpty() {
		return this.root.size() == 0;
	}
}
