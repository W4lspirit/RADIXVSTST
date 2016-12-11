package test.hybrid;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;

import algo.HybridTrie;
import algo.PatriciaTrie;
import parser.FilesParser;

public class insertionNodeTest {

	@Test
	public void simpleInsertTest() {
		try (Stream<Path> paths = Files.walk(Paths.get("Shakespeare"))) {
			List<String> files = new ArrayList<>();
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					files.add(filePath.toString());
				}
			});
			FilesParser f = new FilesParser();
			Set<String> set = f.readFiles(files);
			HybridTrie hybridTrie = new HybridTrie();
			PatriciaTrie patriciaTrie = new PatriciaTrie();
			Long start = System.currentTimeMillis();
			System.out.println(set.size());
			for (String string : set) {
				hybridTrie.addWord(string);
			}
			System.out.println("Hybrid");
			System.out.println(System.currentTimeMillis() - start);
			start = System.currentTimeMillis();
			for (String string : set) {
				patriciaTrie.addWord(string);

			}
			System.out.println("Patricia");
			System.out.println(System.currentTimeMillis() - start);
			assertEquals(patriciaTrie.ComptageMots(), hybridTrie.ComptageMots());
		} catch (IOException e) {
			System.err.println("Error while retrieving all the file");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void multipleInsertTest() {
		System.out.println("3fichiers");
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/1henryiv.txt");
		fileNames.add("Shakespeare/othello.txt");
		fileNames.add("Shakespeare/pericles.txt");
		try {
			HybridTrie hybridTrie = new HybridTrie();
			PatriciaTrie patriciaTrie = new PatriciaTrie();
			Long start = System.currentTimeMillis();

			Set<String> set = f.readFiles(fileNames);
			for (String string : set) {
				hybridTrie.addWord(string);
			}
			System.out.println(set.size());
			System.out.println("Hybrid");
			System.out.println(System.currentTimeMillis() - start);
			start = System.currentTimeMillis();
			for (String string : set) {
				patriciaTrie.addWord(string);

			}
			System.out.println("Patricia");
			System.out.println(System.currentTimeMillis() - start);

			HybridTrie hybridTrie2 = patriciaTrie.convert();
			assertEquals(patriciaTrie.ComptageMots(), hybridTrie2.ComptageMots());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
