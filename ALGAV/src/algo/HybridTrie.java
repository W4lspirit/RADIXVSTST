package algo;

import algo.interfaces.ITrie;

import java.util.ArrayList;
import java.util.List;

public class HybridTrie implements ITrie {
    /*******************************************************/
    /*-------------------PARAMETERS------------------------*/
    /*******************************************************/
    private int nbWord;
    private HNode root;

    /*******************************************************/
	/*-------------------PRINCIPAL METHODS-----------------*/

    /*******************************************************/
    @Override
    public boolean search(String word) {
        return contains(word);
    }

    @Override
    public int ComptageMots() {
        return getSize();
    }

    @Override
    public List<String> ListeMots() {

        return words();
    }

    @Override
    public int ComptageNil() {
        return getNbNIL(getRoot());
    }

    @Override
    public int Hauteur() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int ProfondeurMoyenne() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int Prefixe(String word) {
        return wordsWithPrefix(word).size();
    }

    @Override
    public ITrie Suppression(String word) {
        // TODO Auto-generated method stub
        return null;
    }

    /*******************************************************/
	/*-------------------USEFUL METHODS--------------------*/
    /*******************************************************/
    /**
     * Standard contains
     *
     * @param word the key
     * @return true if this symbol table contains @word and false otherwise
     * @throws IllegalArgumentException if @word is null
     */
    public boolean contains(String word) {
        if (word == null) {
            throw new IllegalArgumentException(
                    "argument to contains() word, is null");
        }
        return getVal(word) != null;
    }

    /**
     * Returns the value associated with the given word.
     *
     * @param word the key
     * @return the value associated with the given word if the word is in the HT
     * and null if the word is not in the HT
     * @throws IllegalArgumentException if @word is null
     */
    public String getVal(String word) {
        if (word == null) {
            throw new IllegalArgumentException(
                    "calls getVal() word, with null argument");
        }
        if (word.length() == 0)
            throw new IllegalArgumentException("word must have length >= 1");
        HNode x = get(root, word, 0);
        if (x == null)
            return null;
        return x.getVal();
    }

    /**
     * Returns the subtrie corresponding to the given word
     *
     * @param x    current node
     * @param word the key
     * @param d    the depth
     * @return the node associated with the given word if the word is in the HT
     * and null if the word is not in the node
     * @throws IllegalArgumentException if @word is empty
     */
    private HNode get(HNode x, String word, int d) {
        if (x == null)
            return null;
        if (word.length() == 0)
            throw new IllegalArgumentException("word must have length >= 1");
        char c = word.charAt(d);
        if (c < x.getC())
            return get(x.getLeft(), word, d);
        else if (c > x.getC())
            return get(x.getRight(), word, d);
        else if (d < word.length() - 1)
            return get(x.getMid(), word, d + 1);
        else
            return x;
    }

    /**
     * Return the number of null pointer in a node
     *
     * @param x the node
     * @return if @x is not null the number of null pointer in each subtree
     * otherwise return 1
     */
    private int getNbNIL(HNode x) {
        if (x == null)
            return 1;
        return getNbNIL(x.getLeft()) + getNbNIL(x.getMid())
                + getNbNIL(x.getRight());

    }

    /**
     * Inserts the word-value pair into the HT, overwriting the old value with
     * the new value if the key is already in the HT. If the value is null, this
     * will delete the key from the HT
     *
     * @param word the word
     * @param val  the value
     * @throws IllegalArgumentException if @word is null
     */
    public void put(String word, String val) {
        if (word == null) {
            throw new IllegalArgumentException("calls put() with null word");
        }
        if (!contains(word))
            nbWord++;
        root = put(root, word, val, 0);
    }

    private HNode put(HNode x, String word, String val, int d) {
        char c = word.charAt(d);
        if (x == null) {
            x = new HNode();
            x.setC(c);
        }
        if (c < x.getC())
            x.setLeft(put(x.getLeft(), word, val, d));
        else if (c > x.getC())
            x.setRight(put(x.getRight(), word, val, d));
        else if (d < word.length() - 1)
            x.setMid(put(x.getMid(), word, val, d + 1));
        else
            x.setVal(val);
        return x;
    }

    // TODO NOT FINISH
    private HNode remove(HNode x, String word, int d) {
        char c = word.charAt(d);
        if (x == null) {
            return null;
        }
        if (c < x.getC()) {
            x.setLeft(remove(x.getLeft(), word, d));
        } else if (c > x.getC()) {
            x.setRight(remove(x.getRight(), word, d));
        } else if (d < word.length() - 1) {
            x.setMid(remove(x.getMid(), word, d + 1));
        }
        if (x.getMid() == null) {
            return deleteNode(x);
        }
        return x;
    }

    // http://stackoverflow.com/questions/22337525/how-to-delete-a-node-in-ternary-tree
    private HNode deleteNode(HNode x) {
        HNode y, z = null;
        //CLONE
        y = x;
        if (x.getLeft() == null) {
            z = x.getRight();
        } else if (x.getRight() != null) {
            z = x.getLeft();
            z.setRight(x.getRight());
            y = x;
            while (y.getRight() != null) {
                y = y.getRight();
            }
            y.setRight(x.getRight());

        }
        return z;

    }

    /**
     * Returns the wordlist of the HT
     *
     * @return all words in the HT as a list
     */
    public List<String> words() {
        List<String> wordList = new ArrayList<>();
        collect(root, new StringBuilder(), wordList);
        return wordList;
    }

    /**
     * Returns all of the words in the HT that start with @prefix.
     *
     * @param prefix the prefix
     * @return all of the words in the HT that start with @prefix, as an List
     * @throws IllegalArgumentException if @prefix is null
     */
    public List<String> wordsWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "calls wordsWithPrefix()  with null prefix");
        }
        List<String> wordsList = new ArrayList<>();
        HNode x = get(root, prefix, 0);
        if (x == null)
            return wordsList;
        if (x.getVal() != null)
            wordsList.add(prefix);
        collect(x.getMid(), new StringBuilder(prefix), wordsList);
        return wordsList;
    }



    /**
     * Collect all words in x's subtrees with the given prefix and store them in @wordlist
     *
     * @param x        the current node
     * @param prefix   the prefix
     * @param wordList wordlist
     */
    private void collect(HNode x, StringBuilder prefix, List<String> wordList) {
        if (x == null)
            return;
        collect(x.getLeft(), prefix, wordList);
        if (x.getVal() != null) {
            wordList.add(prefix.toString() + x.getC());
        }
        collect(x.getMid(), prefix.append(x.getC()), wordList);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.getRight(), prefix, wordList);
    }

    /*******************************************************/
	/*-------------------GETTER/SETTER---------------------*/
    /*******************************************************/
    /**
     * @return the size
     */
    public int getSize() {
        return nbWord;
    }

    /**
     * @return the root
     */
    public HNode getRoot() {
        return root;
    }

}
