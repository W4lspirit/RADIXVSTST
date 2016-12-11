package test.picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import algo.HybridTrie;
import algo.PatriciaTrie;
import parser.FilesParser;

public class SvgDrawTest {

	@Test
	public void test() {
		HybridTrie hybridTrie = new HybridTrie();
		hybridTrie.addWord("tet");
		hybridTrie.addWord("ta");
		hybridTrie.addWord("a");
		hybridTrie.addWord("at");
		hybridTrie.addWord("wt");
		hybridTrie.addWord("ti");
		hybridTrie.addWord("te");
		hybridTrie.addWord("test1");
		// hybridTrie.addWord("tet");
		PatriciaTrie patriciaTrie = hybridTrie.convert();

		System.out.println(patriciaTrie.draw(0));
	}

	@Test
	public void testLargerP() {
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/test.txt");
		PatriciaTrie patriciaTrie3 = new PatriciaTrie();
		try {
			Set<String> set = f.readFiles(fileNames);
			for (String string : set) {
				patriciaTrie3.addWord(string);
			}
			System.out.println(patriciaTrie3.draw(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testLargerH() {
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/test.txt");
		HybridTrie hybridTrie = new HybridTrie();
		try {
			Set<String> set = f.readFiles(fileNames);
			List<String> l = new ArrayList<>(set);
			Collections.shuffle(l);
			for (String string : l) {
				hybridTrie.addWord(string);
			}
			System.out.println(hybridTrie.draw());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
