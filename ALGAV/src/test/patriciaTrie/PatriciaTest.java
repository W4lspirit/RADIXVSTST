package test.patriciaTrie;

import algo.PatriciaTrie;

public class PatriciaTest {

	public static void main(String[] args) {

		PatriciaTrie root = new PatriciaTrie();

		/*
		 * FilesParser f = new FilesParser(); List<String> l = new
		 * ArrayList<String>(); Set<String> words = null; l.add("hamlet.txt");
		 * try { words = f.readFiles(l); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 * 
		 * for(String s : words){ root.addWord(s); }
		 * 
		 * List<String> lwords = root.ListeMots();
		 * System.out.println(root.ComptageMots());
		 * System.out.println(root.Hauteur());
		 * System.out.println(root.ProfondeurMoyenne());
		 */

		System.out.println(root.ComptageNil());

		root.addWord("tester");
		System.out.println(root.ComptageNil());
		root.addWord("slow");
		root.addWord("slower");
		root.addWord("water");
		root.addWord("test");
		root.addWord("team");
		root.addWord("toast");

		System.out.println(root.ComptageNil());


		root.Suppression("slow");

		boolean b = root.search("tester");
		System.out.println(b);
		b = root.search("tes");
		System.out.println(b);
		b = root.search("t");
		System.out.println(b);
		b = root.search("slower");
		System.out.println(b);

		System.out.println(root.ComptageMots());
		System.out.println(root.Hauteur());
		System.out.println(root.ProfondeurMoyenne());

		System.out.println(root.Prefixe("te"));

	}

}
