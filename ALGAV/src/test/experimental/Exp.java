package test.experimental;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import algo.HybridTrie;
import algo.PatriciaTrie;
import parser.FilesParser;

public class Exp {

	public static void main(String[] args) {
		try (Stream<Path> paths = Files.walk(Paths.get("Shakespeare"))) {

			List<String> files = new ArrayList<>();
			paths.forEach(filePath -> {
				System.out.println();
				long start;
				if (Files.isRegularFile(filePath)) {
					files.add(filePath.toString());
					FilesParser f = new FilesParser();
					Set<String> set;
					try {
						set = f.readFiles(files);

						HybridTrie hybridTrie = new HybridTrie();
						PatriciaTrie patriciaTrie = new PatriciaTrie();
						// ajout successif
						// construction complete Patricia
						start = System.currentTimeMillis();

						for (String string : set) {

							patriciaTrie.addWord(string);

						}

						System.out.println("Patricia AJOUT " + " nbW " + set.size() + " time "
								+ (System.currentTimeMillis() - start) + " profondeur_moyenne "
								+ patriciaTrie.ProfondeurMoyenne());

						// construction complete Hybrid
						start = System.currentTimeMillis();

						for (String string : set) {

							hybridTrie.addWord(string);
						}

						System.out.println(
								"Hybrid AJOUT " + " nbW " + set.size() + " time " + (System.currentTimeMillis() - start)
										+ " profondeur_moyenne " + hybridTrie.ProfondeurMoyenne());

						// add new ord
						HybridTrie hybridTrie2 = patriciaTrie.convert();
						PatriciaTrie patriciaTrie2 = hybridTrie.convert();
						start = System.currentTimeMillis();
						hybridTrie2.addWord("Isagogique");// Isagogique
						System.out.println("Hybrid AJOUTSimple " + " nbW " + (set.size() + 1) + " time "
								+ (System.currentTimeMillis() - start) + " profondeur_moyenne "
								+ hybridTrie2.ProfondeurMoyenne());

						start = System.currentTimeMillis();
						patriciaTrie2.addWord("Isagogique");// Isagogique
						System.out.println("Patricia AJOUTSimple " + " nbW " + (set.size() + 1) + " time "
								+ (System.currentTimeMillis() - start) + " profondeur_moyenne "
								+ patriciaTrie2.ProfondeurMoyenne());

						// supress words

						start = System.currentTimeMillis();
						String[] test = set.toArray(new String[set.size()]);
						for (int i = 0; i < 10; i++) {
							hybridTrie2.Suppression(test[i]);
						}
						System.out.println("Hybrid Supression " + " nbW " + (set.size() + 1) + " time "
								+ (System.currentTimeMillis() - start) + " profondeur_moyenne "
								+ hybridTrie2.ProfondeurMoyenne());

						start = System.currentTimeMillis();
						for (int i = 0; i < 10; i++) {
							patriciaTrie2.Suppression(test[i]);
						}
						System.out.println("Patricia Suppression " + " nbW " + (set.size() + 1) + " time "
								+ (System.currentTimeMillis() - start) + " profondeur_moyenne "
								+ patriciaTrie2.ProfondeurMoyenne());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			FilesParser f = new FilesParser();
			Set<String> set = f.readFiles(files);
			HybridTrie hybridTrie = new HybridTrie();
			PatriciaTrie patriciaTrie = new PatriciaTrie();
			long start;
			// construction complete Patricia
			start = System.currentTimeMillis();
			for (String string : set) {
				patriciaTrie.addWord(string);

			}

			System.out.println("construction complete Patricia time " + (System.currentTimeMillis() - start)+" pm "+patriciaTrie.ProfondeurMoyenne());
			List<String> list = new ArrayList<>(set);
			Collections.shuffle(list);
			// construction complete Hybrid
			start = System.currentTimeMillis();
			
			for (String string : list) {
				hybridTrie.addWord(string);
			}

			System.out.println("construction complete hybrid time " + (System.currentTimeMillis() - start) +" profondeur moy "+hybridTrie.ProfondeurMoyenne());
			System.out.println(set.size());
		} catch (IOException e) {
			System.err.println("Error while retrieving all the file");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
