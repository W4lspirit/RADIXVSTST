package test.picasso;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import algo.HybridTrie;
import parser.FilesParser;

public class PicassoPanel extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		FilesParser f = new FilesParser();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("Shakespeare/1henryiv.txt");
		HybridTrie hybridTrie = new HybridTrie();
		try {
			Set<String> set = f.readFiles(fileNames);
			for (String string : set) {
				hybridTrie.addWord(string);
			}
			hybridTrie.draw(g);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.add(new PicassoPanel());
		jFrame.setSize(500, 500);
		jFrame.setVisible(true);
	}
}
