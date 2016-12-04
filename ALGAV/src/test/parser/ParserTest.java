package test.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import parser.FilesParser;

public class ParserTest {

	@Test
	public void test() {
		FilesParser filesParser = new FilesParser();

		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/1henryiv.txt");
		Set<String> set = null;
		try {
			set = filesParser.readFiles(fileNames);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		set.forEach(System.out::println);
	}

}
