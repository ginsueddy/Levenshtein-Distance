package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner dictionary = new Scanner(new File ("dictionary words.txt"));
        Set<String> dictionaryWords = new HashSet<>();
        Map<String, Set<String>> nextWordPossiblites = new HashMap<>();
        Set<String> wordPossiblies = new HashSet<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Give me Word 1: ");
        String currentWord = sc.next().toLowerCase();
        System.out.println("Give me Word 2: ");
        String targetWord = sc.next().toLowerCase();
        System.out.println();

        int levenshteinDistance = 0;
        boolean foundIt = false;

        while (dictionary.hasNext()) {
            dictionaryWords.add(dictionary.next());
        }

        if(currentWord.length() == targetWord.length() && dictionaryWords.contains(currentWord) && dictionaryWords.contains(targetWord)) {
            while (!currentWord.equalsIgnoreCase(targetWord)) {
                if(nextWordPossiblites.keySet().contains(currentWord)){
                    break;
                }
                Iterator<String> itr = dictionaryWords.iterator();
                while (itr.hasNext()) {
                    String dicWord = itr.next();
                    if (dicWord.length() != currentWord.length()) {
                        itr.remove();
                    } else {
                        int similartiy = 0;
                        for (int i = 0; i < dicWord.length(); i++) {
                            if (currentWord.charAt(i) == dicWord.charAt(i)) {
                                similartiy++;
                            }
                        }
                        if (similartiy == dicWord.length() - 1) {
                            wordPossiblies.add(dicWord);
                        }
                    }
                }
                nextWordPossiblites.put(currentWord, wordPossiblies);
                dictionaryWords.remove(currentWord);

                int wordPointHighScore = 0;
                for (String word :
                        nextWordPossiblites.get(currentWord)) {
                    int points = 0;
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) == targetWord.charAt(i)) {
                            points++;
                        }
                    }
                    if (points > wordPointHighScore) {
                        currentWord = word;
                        wordPointHighScore = points;
                    }
                }
                System.out.println(currentWord);
                levenshteinDistance++;
                if(currentWord.equalsIgnoreCase(targetWord)){
                    foundIt = true;
                }
            }
            if(foundIt == true) {
                System.out.println();
                System.out.println("The Levenshtein Distance is " + levenshteinDistance);
            }
            else {
                System.out.println();
                System.out.println("Pathway does not exist");
            }
        }
        else{
            System.out.println("Those are invalid inputs");
        }
    }
}
