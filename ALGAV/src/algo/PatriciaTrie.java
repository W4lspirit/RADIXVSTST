package algo;

import algo.interfaces.ITrie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatriciaTrie implements ITrie {

	/*******************************************************/
	/*-------------------PARAMETERS-----------------*/

	/*******************************************************/

	private static final char ENDCHAR = Character.MIN_VALUE;

	private List<PatriciaTrie> nodes;
	private String value;
	private Character last;

	/*******************************************************/
	/*-------------------TOOL METHODES-----------------*/

	/*******************************************************/

	public PatriciaTrie(String value) {
		this.value = value;
		nodes = new ArrayList<>();
		last = 'n';
	}

	public PatriciaTrie(String value, boolean isWord) {
		this.value = value;
		nodes = new ArrayList<>();
		if (isWord)
			last = ENDCHAR;
		else
			last = 'n';
	}

	public PatriciaTrie(PatriciaTrie p, String suffix) {
		nodes = p.nodes;
		value = suffix;
		last = p.last;

	}

	public PatriciaTrie() {
		value = "";
		nodes = new ArrayList<>();
		last = 'n';
	}

	public boolean isLeaf() {
		return nodes.size() == 0;
	}

	public boolean isWord() {
		return last == ENDCHAR;
	}

	public String getWord() {
		return "";
	}

	public void addWord(String word) {
		int charFound = 0;

		for (PatriciaTrie p : nodes) {
			int min;
			if (word.length() >= p.value.length()) {
				min = p.value.length();
			} else {
				min = word.length();

			}

			for (int i = 0; i < min; i++) {
				if (word.charAt(i) != p.value.charAt(i))
					break;
				charFound++;
			}

			if (charFound != 0) {
				String temp = word.substring(charFound);
				if (charFound == p.value.length()) {
					if (p.isLeaf()) {
						p.last = 'n';
						p.nodes.add(new PatriciaTrie("", true));
						p.nodes.add(new PatriciaTrie(temp, true));
						return;
					}
					p.addWord(temp);
					return;
				}
				String prefix = p.value.substring(0, charFound);
				String suffix = p.value.substring(charFound);
				PatriciaTrie pat = new PatriciaTrie(p, suffix);
				p.nodes = new ArrayList<>();
				p.last = 'n';
				p.value = prefix;
				p.nodes.add(pat);
				p.nodes.add(new PatriciaTrie(temp, true));
				return;

			}

		}

		nodes.add(new PatriciaTrie(word, true));

	}

	/*******************************************************/
	/*-------------------PRINCIPAL METHODS-----------------*/

	/*******************************************************/

	@Override
	public boolean search(String word) {
		int min;
		int charFound = 0;
		for (PatriciaTrie p : nodes) {
			if (word.equals("") && p.value.equals("") && p.last == ENDCHAR)
				return true;
			if (word.length() >= p.value.length()) {
				min = p.value.length();
			} else {
				min = word.length();

			}
			for (int i = 0; i < min; i++) {
				if (word.charAt(i) != p.value.charAt(i))
					break;
				charFound++;
			}
			if (charFound != 0) {
				String temp = word.substring(charFound);
				if (charFound == p.value.length()) {
					if (p.last == ENDCHAR && word.equals(p.value))
						return true;
				}

				if (isLeaf())
					return false;
				return p.search(temp);

			}
		}
		return false;
	}

	@Override
	public int ComptageMots() {
		int cpt = 0;
		if (isWord())
			cpt++;
		for (PatriciaTrie p : nodes) {
			cpt += p.ComptageMots();
		}
		return cpt;
	}

	@Override
	public List<String> ListeMots() {
		List<String> words = new ArrayList<>();

		for (PatriciaTrie p : nodes) {
			p.buildWords(words, p.value);
		}
		Collections.sort(words);
		return words;
	}

	private void buildWords(List<String> words, String s) {

		if (last == ENDCHAR)
			words.add(s);
		if (isLeaf())
			return;

		for (PatriciaTrie p : nodes) {
			p.buildWords(words, s + p.value);
		}

	}

	@Override
	public int ComptageNil() {
		int cpt = 128 - nodes.size();
		for (PatriciaTrie p : nodes) {
			cpt += p.ComptageNil();
		}
		return cpt;
	}

	@Override
	public int Hauteur() {

		int cpt = 0;
		int temp = cpt;
		for (PatriciaTrie p : nodes) {
			int max = temp;
			max += p.HauteurNodes();
			if (max > cpt)
				cpt = max;
		}
		return cpt;
	}

	public int HauteurNodes() {
		int cpt = 1;
		int temp = cpt;
		for (PatriciaTrie p : nodes) {
			int max = temp;
			max += p.HauteurNodes();
			if (max > cpt)
				cpt = max;
		}
		return cpt;
	}

	@Override
	public int ProfondeurMoyenne() {
		int cpt = 1;
		if (isLeaf())
			return cpt;
		int temp = cpt;
		int moy = 0;

		for (PatriciaTrie p : nodes) {
			moy += (temp + p.ProfondeurMoyenne());
		}
		cpt = moy / nodes.size();

		return cpt;

	}

	@Override
	public int Prefixe(String word) {
		int min;
		int charFound = 0;
		for (PatriciaTrie p : nodes) {
			if (word.length() >= p.value.length()) {
				min = p.value.length();
			} else {
				min = word.length();

			}
			for (int i = 0; i < min; i++) {
				if (word.charAt(i) != p.value.charAt(i))
					break;
				charFound++;
			}
			if (charFound != 0) {
				String temp = word.substring(charFound);

				if (temp.equals(""))
					return p.ComptageMots();
				return p.Prefixe(temp);

			}
		}
		return 0;
	}

	@Override
	public ITrie Suppression(String word) {

		int min;
		int charFound = 0;
		for (int j = 0; j < nodes.size(); j++) {
			PatriciaTrie p = nodes.get(j);
			if (word.length() >= p.value.length()) {
				min = p.value.length();
			} else {
				min = word.length();

			}
			for (int i = 0; i < min; i++) {
				if (word.charAt(i) != p.value.charAt(i))
					break;
				charFound++;
			}
			if (charFound != 0 || word.equals("")) {
				String temp = word.substring(charFound);
				if (word.equals(p.value) && p.last == ENDCHAR) {
					if (p.isLeaf()) {
						nodes.remove(j);
						if (nodes.size() == 1) {
							PatriciaTrie son = nodes.get(0);
							last = son.last;
							value += son.value;
							nodes = son.nodes;
						}
					}

					return null;

				}

				p.Suppression(temp);
				return null;
			}

		}
		return null;
	}

	public void Fusion(PatriciaTrie p) {
		p.buildFusionTree(this, "");

	}

	private void buildFusionTree(PatriciaTrie p, String s) {

		if (last == ENDCHAR)
			p.addWord(s);
		if (isLeaf())
			return;

		for (PatriciaTrie p2 : nodes) {
			p2.buildFusionTree(p, s + p2.value);
		}
	}

	public HybridTrie convert() {

		HybridTrie h = new HybridTrie();
		HNode root = h.getRoot();
		HNode hnode;
		for (PatriciaTrie p : nodes) {
			hnode = root;
			if (p.value.equals("")) {
				hnode.setEnd(true);
				break;
			}

			if (hnode.getMid() == null) {
				hnode.setMid(new HNode());
				hnode = hnode.getMid();
			} else {
				hnode = getNextNode(hnode.getMid(), p.value.charAt(0));
			}

			hnode.setVal(p.value.charAt(0));
			for (int i = 1; i < p.value.length(); i++) {
				hnode.setMid(new HNode());
				hnode.getMid().setVal(p.value.charAt(i));
				hnode = hnode.getMid();
			}

			if (p.isLeaf()) {
				hnode.setEnd(true);
				break;
			}

			p.convertNode(hnode);

		}
		return h;

	}

	private void convertNode(HNode hnode) {

		HNode temp;
		temp = hnode;

		for (PatriciaTrie p : nodes) {

			hnode = temp;

			if (p.value.equals("")) {
				temp.setEnd(true);

			} else {

				if (hnode.getMid() == null) {
					hnode.setMid(new HNode());
					hnode = hnode.getMid();
				} else {
					hnode = getNextNode(hnode.getMid(), p.value.charAt(0));
				}

				hnode.setVal(p.value.charAt(0));
				for (int i = 1; i < p.value.length(); i++) {
					hnode.setMid(new HNode());
					hnode.getMid().setVal(p.value.charAt(i));
					hnode = hnode.getMid();
				}

				if (p.isLeaf()) {
					hnode.setEnd(true);

				} else {
					p.convertNode(hnode);
				}

			}
		}

	}

	private HNode getNextNode(HNode hnode, char charAt) {
		if (charAt < hnode.getVal()) {
			if (hnode.getLeft() == null) {
				hnode.setLeft(new HNode());
				return hnode.getLeft();
			}
			return getNextNode(hnode.getLeft(), charAt);
		} else {
			if (hnode.getRight() == null) {
				hnode.setRight(new HNode());
				return hnode.getRight();
			}
			return getNextNode(hnode.getRight(), charAt);
		}
	}

	@Override
	public String toString() {

		return value + "/" + last;
	}

	public void addNodes(List<PatriciaTrie> tries) {
		nodes.addAll(tries);

	}

	public void addNode(PatriciaTrie tries) {
		nodes.add(tries);

	}

	public void setLast(boolean end) {
		if (end)
			last = ENDCHAR;
		else
			last = 'n';
	}

}
