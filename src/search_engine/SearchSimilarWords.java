package search_engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SearchSimilarWords {

	// Searches for similar words in the database.
	public static void searchSimilar(String keyword, int numberofResults, InvertedIndex invertedIndex) {
		HashMap<String, Integer> sortedDistance = sortByEditDistance(keyword, invertedIndex);
		for (Entry<String, Integer> me : sortedDistance.entrySet()) {
			String word = me.getKey().toString();
			System.out.println("Did you mean : " + word);

			// Searching for the word with lowest distance.
			Map<String, Integer> sortedMap;
			ArrayList<String> as = invertedIndex.find(word);
			String phrase = word.toLowerCase();

			sortedMap = SortResults.sortByRank(as, phrase);
			Search.printResult(sortedMap, numberofResults);
			break;
		}

	}

	private static HashMap<String, Integer> sortByEditDistance(String keyword, InvertedIndex ps) {
		HashMap<String, Integer> indexDistance = new HashMap<>();
		Iterator<Entry<String, HashSet<Integer>>> iterator = ps.index.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, HashSet<Integer>> me = iterator.next();
			String word = me.getKey().toString();
			int distance = EditDistance.editDistance(word, keyword);
			if (distance < 3 && distance != 0) {
				indexDistance.put(word, distance);
			}
		}

		HashMap<String, Integer> sortedDistance = SortResults.sortValues(indexDistance);
		return sortedDistance;
	}
}
