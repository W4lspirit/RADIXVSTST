package test.hybrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import algo.HybridTrie;
import algo.PatriciaTrie;
import parser.FilesParser;

public class ConvertHybridTest {

	@Test
	public void convertSimple() {
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
		PatriciaTrie patriciaTrie2 = new PatriciaTrie();
		patriciaTrie2.addWord("test");
		for (String string : patriciaTrie.ListeMots()) {

			System.out.println(" PAtricia " + string);

		}
		assertEquals(hybridTrie.ComptageMots(), patriciaTrie.ComptageMots());
	}

	@Test
	public void multipleInsertThenConvertTest() {
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/1henryiv.txt");
		PatriciaTrie patriciaTrie3 = new PatriciaTrie();
		HybridTrie hybridTrie = new HybridTrie();
		try {
			Set<String> set = f.readFiles(fileNames);
			for (String string : set) {
				hybridTrie.addWord(string);
				patriciaTrie3.addWord(string);
			}

			PatriciaTrie patriciaTrie = hybridTrie.convert();
			boolean error = false;
			for (String string : patriciaTrie.ListeMots()) {

				if (!patriciaTrie3.search(string)) {
					error = true;
					System.out.println(string);
				}

			}

			System.out.println(set.size());
			System.out.println(patriciaTrie3.ComptageMots());
			assertFalse(error);
			assertEquals(hybridTrie.ComptageMots(), patriciaTrie.ComptageMots());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
