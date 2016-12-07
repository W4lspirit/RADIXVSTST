package algo;

import algo.interfaces.ITrie;

import java.util.Collections;
import java.util.List;

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
	private void draw() {
		HNode.draw(root, 0, 0);
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
