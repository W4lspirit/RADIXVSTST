package algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algo.interfaces.ITrie;

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
		nodes = new ArrayList<PatriciaTrie>();
		last = 'n';
	}

	public PatriciaTrie(String value, boolean isWord) {
		this.value = value;
		nodes = new ArrayList<PatriciaTrie>();
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
		nodes = new ArrayList<PatriciaTrie>();
		last = 'n';
	}

	public boolean isLeaf() {
		if (nodes.size() == 0)
			return true;
		return false;
	}

	public boolean isWord() {
		if (last == ENDCHAR)
			return true;
		return false;
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
				p.nodes = new ArrayList<PatriciaTrie>();
				p.last = 'n';
				p.value = prefix;
				p.nodes.add(pat);
				p.nodes.add(new PatriciaTrie(temp, true));
				return;

			}

		}

		nodes.add(new PatriciaTrie(word, true));
		return;

	}

	/*******************************************************/
	/*-------------------PRINCIPAL METHODS-----------------*/

	/*******************************************************/

	@Override
	public boolean search(String word) {
		int min = 0;
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
		List<String> words = new ArrayList<String>();

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
		return;

	}

	@Override
	public int ComptageNil() {
		return 0;
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
		int min = 0;
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

		int min = 0;
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

	@Override
	public String toString() {
		
		return value + "/"+last;
	}

}
