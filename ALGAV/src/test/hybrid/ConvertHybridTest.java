package test.hybrid;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import algo.HNode;
import algo.HybridTrie;
import algo.PatriciaTrie;
import parser.FilesParser;

public class ConvertHybridTest {

	@Test
	public void convertSimple() {
		HybridTrie hybridTrie = new HybridTrie();
		hybridTrie.addWord("te");
		hybridTrie.addWord("ta");
		 hybridTrie.addWord("ti");
		 hybridTrie.addWord("te");
		// hybridTrie.addWord("test1");
		// hybridTrie.addWord("tet");
		PatriciaTrie patriciaTrie = hybridTrie.convert();
		PatriciaTrie patriciaTrie2 = new PatriciaTrie();
		patriciaTrie2.addWord("test");
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
			int cpt = 0;

			for (String string : patriciaTrie.ListeMots()) {

				System.out.println(++cpt + " PAtricia " + string);

			}
			System.out.println(hybridTrie.search("abhorred"));
			System.out.println(set.size());
			System.out.println(patriciaTrie3.ComptageMots());
			assertEquals(hybridTrie.ComptageMots(), patriciaTrie.ComptageMots());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
