package algo;

import java.util.ArrayList;
import java.util.List;

public class HNode {
	/*******************************************************/
	/*-------------------PARAMETERS------------------------*/
	/*******************************************************/
	private char val;
	private HNode left, mid, right;
	private boolean end = false;

	/*******************************************************/
	/*-------------------CONSTRUCTORS----------------------*/

	/*******************************************************/

	public HNode(char c) {
		this.val = c;
	}

	public HNode() {
	}

	/*******************************************************/
	/*-------------------PRINCIPAL METHODS-----------------*/

	/*******************************************************/

	public static HNode insert(HNode node, String word) {
		char x = word.charAt(0);

		if (node == null) {
			node = new HNode(x);

		}
		if (x < node.val) {
			node.left = insert(node.left, word);
		} else if (x > node.val) {
			node.right = insert(node.right, word);
		} else {
			if (word.length() == 1) {
				node.setEnd(true);
			} else {
				node.mid = insert(node.mid, word.substring(1));
			}
		}
		return node;
	}

	public static HNode delete(HNode node, String word) {
		if (node == null) {
			return null;
		} else {
			char x = word.charAt(0);
			if (x < node.val) {
				node.left = delete(node.left, word);
			} else if (x > node.val) {
				node.right = delete(node.right, word);
			} else {
				if (word.length() == 1) {
					if (node.end) {
						node = swapNode(node);
					}
					return node;
				} else {
					node.mid = delete(node.mid, word.substring(1));
				}
			}
			if (node.mid == null && node.left == null && node.right == null && !node.end) {
				node = null;
			} else if (node.mid == null && node.left != null) {
				node.mid = node.left;
				node.left = null;
			} else if (node.mid == null && node.right != null) {
				node.mid = node.right;
				node.right = null;
			}
			return node;

		}
	}

	private static HNode swapNode(HNode node) {
		if (node.mid != null) {
			node.end = false;
		} else if (node.left != null) {
			node.end = false;
			node.mid = node.left;
			node.left = null;

		} else if (node.right != null) {
			node.end = false;
			node.mid = node.right;
			node.right = null;
		} else {
			node = null;
		}
		return node;
	}

	public static int averageDepth(HNode node) {
		int levels = getLevels(node);
		int leafs = getNumberOfLeaf(node, 0);
		if (levels == 0)
			return 0;
		return (leafs / levels);
	}

	public static boolean search(HNode node, String word) {
		if (node == null) {
			return false;
		} else {
			char x = word.charAt(0);
			if (x < node.val) {
				return search(node.left, word);
			} else if (x > node.val) {
				return search(node.right, word);
			} else {
				if (word.length() == 1) {
					return node.end;
				}
				return search(node.mid, word.substring(1));
			}
		}

	}

	public static int prefix(HNode node, String prefix) {
		if (prefix.length() == 0) {
			return getNumberOfWords(node);
		}
		char x = prefix.charAt(0);
		if (x < node.val) {
			return prefix(node.left, prefix);
		} else if (x > node.val) {
			return prefix(node.right, prefix);
		} else {
			return prefix(node.mid, prefix.substring(1));
		}
	}

	/**
	 * Convert a ternary node into a patricia trie
	 * 
	 * @param parent
	 *            PatriciaTrie
	 * @param node
	 *            current node
	 * @param prefix
	 *            the prefix
	 * @param mid
	 *            true if the fonction is apply to a middle node
	 */
	public static void convert(PatriciaTrie parent, HNode node, String prefix, boolean mid) {
		// System.out.println(parent.toString());
		if (node != null) {
			// System.out.println(node.toString());
			if (isLeaf(node)) {
				parent.addNode(new PatriciaTrie(prefix + node.val, node.isEnd()));
			} else if (!hasNeighbour(node)) {
				PatriciaTrie tmp = parent;
				prefix += node.val;
				if (node.isEnd()) {
					// add intermediate Patrica
					tmp = new PatriciaTrie(prefix, false);
					parent.addNode(tmp);
					// add end word
					tmp.addNode(new PatriciaTrie("", true));
					// reset prefix
					prefix = "";
				}
				// convert middle node
				convert(tmp, node.mid, prefix, true);
			} else {

				PatriciaTrie tmp = parent;
				// add intermediate Patrica to the parent if the current node
				// was a middle node
				if (mid) {
					// the new Patrica is the new parent
					tmp = new PatriciaTrie(prefix, false);
					parent.addNode(tmp);
				}
				// add left and right node to parent
				convert(tmp, node.left, "", false);
				convert(tmp, node.right, "", false);
				if (node.isEnd()) {
					// add intermediate Patrica
					PatriciaTrie tmp2 = new PatriciaTrie("" + node.val, false);
					tmp.addNode(tmp2);
					// add end word
					tmp2.addNode(new PatriciaTrie("", true));
					// convert into tmp2
					convert(tmp2, node.mid, "", true);
				} else {
					// convert into tmp
					convert(tmp, node.mid, "" + node.val, true);
				}

			}
		}
	}

	public static List<String> getWords(HNode node, String prefix) {
		List<String> words = new ArrayList<>();
		if (node != null) {

			words.addAll(getWords(node.left, prefix));
			StringBuilder s = new StringBuilder(prefix);
			s.append(node.val);
			if (node.end) {
				words.add(s.toString());
			}
			words.addAll(getWords(node.mid, s.toString()));
			words.addAll(getWords(node.right, prefix));
		}

		return words;
	}

	public static int height(HNode node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + Math.max(Math.max(height(node.mid), height(node.left)), height(node.right));
		}

	}

	public static int countNP(HNode node) {
		if (node == null) {
			return 1;
		} else {
			return countNP(node.mid) + countNP(node.left) + countNP(node.right);
		}

	}

	public static int getNumberOfWords(HNode node) {
		if (node == null) {
			return 0;
		} else {
			int val = 0;
			if (node.end) {
				val = 1;
			}
			return val + getNumberOfWords(node.mid) + getNumberOfWords(node.left) + getNumberOfWords(node.right);
		}
	}

	/*******************************************************/
	/*-------------------USEFUL METHODS--------------------*/

	/*******************************************************/

	private static int getLevels(HNode node) {
		if (node == null)
			return 0;
		return 1 + getLevels(node.mid) + getLevels(node.left) + getLevels(node.right);
	}

	private static int getNumberOfLeaf(HNode node, int depth) {
		if (node == null)
			return 0;
		return depth + getNumberOfLeaf(node.mid, depth + 1) + getNumberOfLeaf(node.left, depth + 1)
				+ getNumberOfLeaf(node.right, depth + 1);
	}

	private static boolean isBalanced(HNode node) {
		if (node == null)
			return true;
		else {
			if (node.end)
				return true;
			return isBalanced(node.mid) && isBalanced(node.left) && isBalanced(node.right)
					&& Math.abs(height(node.right) - height(node.left)) < 3;
		}
	}

	// TODO

	static int balance(HNode node) {
		HNode tmp = node;
		int ll = 0, lc = 0, lr = 0;
		int rl = 0, rc = 0, rr = 0;
		int l = height(node.left);
		int c = height(node.mid);
		int r = height(node.right);
		if (c > 1 + Math.max(l, r)) {
			// center to left if left is amx
			// center to right if right is max
			/*
			 * then apply a center-to-left/right transformation
			 */
		}
		HNode left = tmp.left;
		if (left != null) {
			ll = height(left.left);
			lc = height(left.mid);
			lr = height(left.right);
		}
		if (l > 1 + Math.max(c, r) && lc <= Math.max(ll, lr)) {
			// and is not tnodeR
			// aply left to center
		} else {
			if (l > 1 + Math.max(c, r) && lc > Math.max(ll, lr)) {
				// not tnodeR
				// then apply a center-to-left/right transformation followed by
				// a left-to-center transfor-
				// mation
			} else {
				HNode right = tmp.right;
				if (right != null) {
					rl = height(right.left);
					rc = height(right.mid);
					rr = height(right.right);
				}
				if (r > 1 + Math.max(c, l) && rc <= Math.max(rl, rr)) {
					// not tnodel
					// then apply a right-to-center transformation
				} else {
					if (r > 1 + Math.max(c, l) && rc > Math.max(rl, rr)) {
						// not tnodel
						// then apply a center-to-left/right transformation
						// followed by a right-to-center trans-
						// formation
					}
				}
			}
		}

		/*
		 * If a points to the root then end the procedure, or set a to its
		 * parent and continue from Step 2
		 */
		return 0;
	}

	/*******************************************************/
	/*-------------------GETTER/SETTER---------------------*/
	/*******************************************************/

	/**
	 * @return the left
	 */
	public HNode getLeft() {
		return left;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(HNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public HNode getRight() {
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(HNode right) {
		this.right = right;
	}

	/**
	 * @return the end
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	/**
	 * @return the val
	 */
	public char getVal() {
		return val;
	}

	/**
	 * @param val
	 *            the val to set
	 */
	public void setVal(char val) {
		this.val = val;
	}

	/**
	 * @return the mid
	 */
	public HNode getMid() {
		return mid;
	}

	/**
	 * @param mid
	 *            the mid to set
	 */
	public void setMid(HNode mid) {
		this.mid = mid;
	}

	private static boolean isLeaf(HNode node) {
		return node.end && node.mid == null && node.left == null && node.right == null;
	}

	private static boolean hasNeighbour(HNode node) {
		return node.left != null || node.right != null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String v = val + "\n";
		String n = "<";
		if (left != null)
			n += left.val + ",";
		if (mid != null)
			n += mid.val + ",";
		if (right != null)
			n += right.val;
		n += ">\n";
		return v + n;
	}

	public static String draw(HNode root, int i) {
		if (root == null)
			return "";
		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < i; j++) {
			builder.append(" ");
		}
		builder.append(root.val);
		if (root.end) {
			builder.append('*');
		}
		builder.append("\n");
		i++;
		builder.append(draw(root.left, i));
		builder.append(draw(root.mid, i));
		builder.append(draw(root.right, i));
		return builder.toString();
	}

}
