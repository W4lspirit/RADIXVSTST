package algo;

import java.awt.Graphics;
import java.util.Collections;
import java.util.List;

import algo.interfaces.ITrie;

public class HybridTrie implements ITrie {
	/*******************************************************/
	/*-------------------PARAMETERS------------------------*/
	/*******************************************************/
	private HNode root;

	/*******************************************************/
	/*-------------------PRINCIPAL METHODS-----------------*/

	/*******************************************************/
	@Override
	public boolean search(String word) {
		return HNode.search(root, word);
	}

	@Override
	public int ComptageMots() {
		return HNode.getNumberOfWords(root);
	}

	@Override
	public List<String> ListeMots() {
		List<String> words = HNode.getWords(root, "");
		Collections.sort(words);
		return words;
	}

	@Override
	public int ComptageNil() {
		return HNode.countNP(root);
	}

	@Override
	public int Hauteur() {
		return HNode.height(root);
	}

	@Override
	public int ProfondeurMoyenne() {
		return HNode.averageDepth(root);
	}

	@Override
	public int Prefixe(String word) {
		return HNode.prefix(root, word);
	}

	@Override
	public ITrie Suppression(String word) {
		root = HNode.delete(root, word);
		return this;
	}

	/*******************************************************/
	/*-------------------USEFUL METHODS--------------------*/
	/*******************************************************/
	public void draw(Graphics g) {
		HNode.draw(g,root, 0, 0);
	}

	public PatriciaTrie convert() {
		List<PatriciaTrie> patriciaTries = HNode.getNeighbour(root, "");
		PatriciaTrie p = new PatriciaTrie();
		p.addNodes(patriciaTries);
		return p;

	}

	public void addWord(String string) {
		root = HNode.insert(root, string);

	}

	/*******************************************************/
	/*-------------------GETTER/SETTER---------------------*/
	/*******************************************************/

	/**
	 * @return the root
	 */
	public HNode getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(HNode root) {
		this.root = root;
	}

}
