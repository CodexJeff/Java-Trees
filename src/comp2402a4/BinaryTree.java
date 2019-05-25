package comp2402a4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class BinaryTree<Node extends BinaryTreeNode<Node>> {
	/**
	 * Used to make a mini-factory
	 */
	public Node sampleNode;

	/**
	 * The root of this tree
	 */
	public Node r;

	/**
	 * This tree's null node
	 */
	public Node nil;

	/**
	 * Create a new instance of this class
	 * @param isampleNode
	 */
	public BinaryTree(Node nil) {
		sampleNode = nil;
		this.nil = null;
	}

	/**
	 * Create a new instance of this class
	 * @warning child must set sampleNode before anything that
	 * might make calls to newNode()
	 */
	public BinaryTree() { }

	/**
	 * Allocate a new node for use in this tree
	 * @return
	 */
	@SuppressWarnings({"unchecked"})
	public Node newNode() {
		try {
			Node u = (Node)sampleNode.getClass().getDeclaredConstructor().newInstance();
			u.parent = u.left = u.right = nil;
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}

	/**
	 * Compute the size (number of nodes) of this tree
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}

	/**
	 * @return the size of the subtree rooted at u
	 */
	public int size(Node u) {
		if (u == nil)
			return 0;
		return 1 + size(u.left) + size(u.right);
	}

	public int height() {
		return height(r);
	}

	/**
	 * @return the size of the subtree rooted at u
	 */
	public int height(Node u) {
		if (u == nil)
			return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}


	/**
	 * @return
	 */
	public boolean isEmpty() {
		return r == nil;
	}

	/**
	 * Make this tree into the empty tree
	 */
	public void clear() {
		r = nil;
	}

	public void traverse(Node u) {
		if (u == nil) return;
		traverse(u.left);
		traverse(u.right);
	}

	public void traverse2() {
		Node u = r, prev = nil, next;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil) next = u.left;
				else if (u.right != nil) next = u.right;
				else next = u.parent;
			} else if (prev == u.left) {
				if (u.right != nil) next = u.right;
				else next = u.parent;
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
	}


	public int size2() {
		Node u = r, prev = nil, next;
		int n = 0;
		while (u != nil) {
			if (prev == u.parent) {
				n++;
				if (u.left != nil) next = u.left;
				else if (u.right != nil) next = u.right;
				else next = u.parent;
			} else if (prev == u.left) {
				if (u.right != nil) next = u.right;
				else next = u.parent;
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return n;
	}



	public void bfTraverse() {
		Queue<Node> q = new LinkedList<Node>();
		q.add(r);
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (u.left != nil) q.add(u.left);
			if (u.right != nil) q.add(u.right);
		}
	}

	/**
	 * Find the first node in an in-order traversal
	 * @return
	 */
	public Node firstNode() {
		Node w = r;
		if (w == nil) return nil;
		while (w.left != nil)
			w = w.left;
		return w;
	}

	/**
	 * Find the node that follows u in an in-order traversal
	 * @param w
	 * @return
	 */
	public Node nextNode(Node w) {
		if (w.right != nil) {
			w = w.right;
			while (w.left != nil)
				w = w.left;
		} else {
			while (w.parent != nil && w.parent.left != w)
				w = w.parent;
			w = w.parent;
		}
		return w;
	}

	public static <Node extends BinaryTreeNode<Node>> void completeBinaryTree(BinaryTree<Node> t, int n) {
		Queue<Node> q = new LinkedList<Node>();
		t.clear();
		if (n == 0)
			return;
		t.r = t.newNode();
		q.add(t.r);
		while (--n > 0) {
			Node u = q.remove();
			u.left = t.newNode();
			u.left.parent = u;
			q.add(u.left);
			if (--n > 0) {
				u.right = t.newNode();
				u.right.parent = u;
				q.add(u.right);
			}
		}
	}

	/**
	 * Create a new full binary tree whose expected number of nodes is n
	 * @param <Node>
	 * @param t
	 * @param n
	 */
	public static <Node extends BinaryTreeNode<Node>> void galtonWatsonFullTree(BinaryTree<Node> t, int n) {
		Random r = new Random();
		Queue<Node> q = new LinkedList<Node>();
		t.clear();
		t.r = t.newNode();
		q.add(t.r);
		double p = (0.5 - 1.0/(n+n));
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (r.nextDouble() < p) {
				u.left = t.newNode();
				u.left.parent = u;
				q.add(u.left);
				u.right = t.newNode();
				u.right.parent = u;
				q.add(u.right);
			}
		}
	}

	static <Node extends BinaryTreeNode<Node>> void galtonWatsonTree(BinaryTree<Node> t, int n) {
		Random r = new Random();
		Queue<Node> q = new LinkedList<Node>();
		t.clear();
		t.r = t.newNode();
		q.add(t.r);
		double p = (0.5 - 1.0/(n+n));
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (r.nextDouble() < p) {
				u.left = t.newNode();
				u.left.parent = u;
				q.add(u.left);
			} if (r.nextDouble() < p) {
				u.right = t.newNode();
				u.right.parent = u;
				q.add(u.right);
			}
		}
	}



}
