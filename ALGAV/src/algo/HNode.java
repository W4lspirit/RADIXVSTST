package algo;

import java.util.ArrayList;
import java.util.List;

public class HNode {
	private static final int JMP_X = 10;
	private static final int JMP_Y = 10;
	/*******************************************************/
	/*-------------------PARAMETERS------------------------*/
	/*******************************************************/
	private char val;
	private HNode left, mid, right;
	private boolean end = false;

	public HNode(char c) {
		this.val = c;
	}

	public HNode() {
		// TODO Auto-generated constructor stub
	}

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
		} else if (word.length() == 0) {
			return node.end;

		} else {
			char x = word.charAt(0);
			if (x < node.val) {
				return search(node.left, word);
			} else if (x > node.val) {
				return search(node.right, word);
			} else {
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



	/*******************************************************/
	/*-------------------USEFUL METHODS--------------------*/
	/*******************************************************/
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

	/* Print plot */
	public static void draw(HNode node, int i, int j) {
		if (node == null) {
			return;
		}
		System.out.println(node.val + i + j);
		draw(node.left, i - (widthL(node.mid) + widthR(node.left) + 1) * JMP_X, j + JMP_Y);
		System.out.println(node.val + i + j);
		draw(node.mid, i, j + JMP_Y);
		System.out.println(node.val + i + j);
		draw(node.right, i - (widthR(node.mid) + widthL(node.right) + 1) * JMP_X, j + JMP_Y);
		System.out.println();

	}

	private static int widthR(HNode node) {
		if (node == null)
			return 0;
		else
			return widthR(node.mid) + width(node.right);
	}

	private static int widthL(HNode node) {
		if (node == null)
			return 0;
		else
			return widthL(node.mid) + width(node.right);
	}

	private static int width(HNode node) {
		if (node == null)
			return 0;
		else {
			if (node.left == null && node.mid == null && node.right == null)
				return 1;
			else
				return (width(node.left) + width(node.mid) + width(node.right));
		}
	}

}
