package search_engine;

import java.util.HashMap;
import java.util.Map;

class Node
 {
    Map<Character, Node> children;
    int listIndex;

    Node()
     {
        children = new HashMap<>();
        listIndex = -1; // -1 means no occurrence list is associated yet
     }
 }

public class Trie
 {
    Node root;
    Trie()
     {
    	root=new Node();
     }
    public void insert(String word, int listIndex)
     {
        Node current = root;
        for (char c : word.toCharArray())
         {
            current.children.putIfAbsent(c, new Node());
            current = current.children.get(c);
         }
        current.listIndex = listIndex;
     }
    public int search(String word)
     {
        Node current = root;
        for (char c : word.toCharArray())
         {
            current = current.children.get(c);
            if (current == null) {
                return -1;
            }
         }
        return current.listIndex;
     }
 }
