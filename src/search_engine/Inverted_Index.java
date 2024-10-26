package search_engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Inverted_Index
 {
	private Trie trie;
    private List<Set<Integer>> occurrenceLists;
    private Map<String, Integer> termFrequency;

    public Inverted_Index()
     {
        trie = new Trie();
        occurrenceLists = new ArrayList<>();
        termFrequency = new HashMap<>();
     }
    public void addDocument(int docId, String content)
     {
        String[] words = content.split("\\W+");
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));

        for (String word : uniqueWords) {
            word = word.toLowerCase();
            int listIndex = trie.search(word);
            if (listIndex == -1)
             {
                listIndex = occurrenceLists.size();
                occurrenceLists.add(new TreeSet<>());//? hashset()
                trie.insert(word, listIndex);
             }
            occurrenceLists.get(listIndex).add(docId);

            termFrequency.put(word, termFrequency.getOrDefault(word, 0) + 1);
        }
    }
    public Set<Integer> search(String word)
     {
        word = word.toLowerCase();
        int listIndex = trie.search(word);
        if (listIndex != -1) {
            return occurrenceLists.get(listIndex);
        }
        return Collections.emptySet();
     }
    public Set<Integer> searchMultipleWords(String[] words)
     {
        List<Set<Integer>> lists = new ArrayList<>();
        for (String word : words)
         {
           Set<Integer> result = search(word);
           if (result.isEmpty())
            {
               return Collections.emptySet(); // If any word is not found, return empty set
            }
           lists.add(result);
         }
        return intersection(lists);
     }
    private Set<Integer> intersection(List<Set<Integer>> lists)
     {
        if (lists.isEmpty())
         {
            return Collections.emptySet();
         }
        Set<Integer> result = new TreeSet<>(lists.get(0));
        for (Set<Integer> list : lists.subList(1, lists.size()))
         {
            result.retainAll(list);
         }
        return result;
     }
    public List<Integer> rankResults(Set<Integer> docIds, String[] query)
     {
        List<Integer> rankedResults = new ArrayList<>(docIds);
        rankedResults.sort((a, b) ->
         {
            int freqA = 0;
            int freqB = 0;
            for (String word : query) {
                freqA += termFrequency.getOrDefault(word.toLowerCase(), 0);
                freqB += termFrequency.getOrDefault(word.toLowerCase(), 0);
            }
            return Integer.compare(freqB, freqA); // Higher frequency first
         });
        return rankedResults;
     }
 }
