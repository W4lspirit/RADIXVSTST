package test.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import algo.HNode;
import parser.FilesParser;

public class TestNodeInsertion {

	@Test
	public void simpleInsertTest() {
		HNode n = null;
		n = HNode.insert(n, "test");
		n = HNode.insert(n, "tata");
		n = HNode.insert(n, "titi");
		n = HNode.insert(n, "tete");
		n = HNode.insert(n, "test1");
		n = HNode.delete(n, "tet");
		assertTrue(HNode.getNumberOfWords(n) == 5);
		n = HNode.delete(n, "tete");
		assertTrue(HNode.getNumberOfWords(n) == 4);
	}

	@Test
	public void multipleInsertTest() {
		HNode n = null;
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/1henryiv.txt");
		try {
			Set<String> set = f.readFiles(fileNames);
			for (String string : set) {
				n = HNode.insert(n, string);

			}
			assertTrue(HNode.getNumberOfWords(n) == 3723);
			n = HNode.delete(n, "tet");
			assertTrue(HNode.getNumberOfWords(n) == 3723);
			n = HNode.delete(n, "a");
			assertTrue(HNode.getNumberOfWords(n) == 3722);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
