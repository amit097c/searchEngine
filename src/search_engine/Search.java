package search_engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Search
{
	// Searches for a phrase in the files using InvertedIndex created by
	// InvertedIndex.java.
	public static void searchPhrase(String keyword, int numberOfResults) {
		InvertedIndex invertedIndex = new InvertedIndex();
		String phrase = keyword;
		ArrayList<String> as = invertedIndex.find(phrase);
		phrase = phrase.toLowerCase();
		if (as == null) {
			SearchSimilarWords.searchSimilar(phrase, numberOfResults, invertedIndex);
		} else {
			Map<String, Integer> sortedMap = SortResults.sortByRank(as, phrase);
			printResult(sortedMap, numberOfResults);
		}
	}

	// Prints given number of results from the provided Map.
	public static void printResult(Map<String, Integer> result, int numberOfResults) {
		Iterator<Entry<String, Integer>> iterator = result.entrySet().iterator();
		int i = 0;
		while (iterator.hasNext() && numberOfResults > i++) {
			Map.Entry<String, Integer> me = iterator.next();
			String fileName = me.getKey().toString();
			File f = new File("WebPages/" + fileName);
			In in = new In(f);
			String url = in.readLine();
			System.out.println("-----------------------------------------");
			System.out.println(
					fileName.substring(0, fileName.length() - 4) + "\t\t Number of Occurences : " + me.getValue());
			System.out.println(url);
		}
	}
}
