package search_engine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InvertedIndex {
	public Map<Integer, String> sources;
	public HashMap<String, HashSet<Integer>> index;

	InvertedIndex() {
		indexBuilding();
	}

	// Builds the InvertedIndex database from files.
	public void indexBuilding() {
		sources = new HashMap<>();
		index = new HashMap<>();
		File directory = new File("WebPages");
		File files[] = directory.listFiles();

		for (int i = 0; i < files.length; i++) {

			String[] words = getWordsFromFile(files[i]);
			sources.put(i, files[i].getName());
			createMap(words, i, files[i].getName());
		}
	}

	// Inserts provided value in the InvertedIndex database
	public void createMap(String[] words, int i, String fileName) {
		for (String word : words) {
			word = word.toLowerCase();
			if (!index.containsKey(word)) {
				index.put(word, new HashSet<>());
			}
			index.get(word).add(i);
		}

	}

	// Searches the database for the given phrase
	public ArrayList<String> find(String phrase) {
		ArrayList<String> fileNames;
		try {
			phrase = phrase.toLowerCase();
			fileNames = new ArrayList<>();
			String[] words = phrase.split("\\W+");
			String hashKey = words[0].toLowerCase();
			if (index.get(hashKey) == null) {
				return null;
			}
			HashSet<Integer> res = new HashSet<>(index.get(hashKey));
			for (String word : words) {
				res.retainAll(index.get(word));
			}

			if (res.size() == 0) {
				return null;
			}
			for (int num : res) {
				fileNames.add(sources.get(num));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return fileNames;
	}

	// Returns clean words from given file
	public static String[] getWordsFromFile(File f) {
		In in = new In(f);
		String text = in.readAll();
		text = text.replaceAll("[^a-zA-Z0-9\\s]", "");
		String[] words = text.split(" ");

		return words;

	}
}
