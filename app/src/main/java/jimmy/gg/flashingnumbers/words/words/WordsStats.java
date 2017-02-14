package jimmy.gg.flashingnumbers.words.words;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ggjimmy on 2/12/17.
 */

public class WordsStats {
    private int lives, score;
    private ArrayList<String> usedWords;
    private ArrayList<String> wordList;
    public int random;
    public int count = 0;
    private boolean isUsed = true;

    public WordsStats(int lives, int score) {
        this.lives = lives;
        this.score = score;
        usedWords = new ArrayList<>();
        wordList = new ArrayList<>();
        random = new Random().nextInt(5) + 1;
    }

    public boolean isInUsed(String word) {
        for (String s : usedWords) {
            if (s.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int bounds) {
        random = new Random().nextInt(bounds);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        this.isUsed = used;
    }

    public void addWord(String word) {
        this.wordList.add(word);
    }

    public String getWords(int index) {
        return this.wordList.get(index);
    }

    public ArrayList<String> getWordList() {
        return this.wordList;
    }

    public ArrayList<String> getUsedWords() {
        return usedWords;
    }

    public String getWord(int index) {
        return this.usedWords.get(index);
    }

    public void addNewWord(String word) {
        this.usedWords.add(word);
    }

    public boolean wordsContains(String word) {
        return this.usedWords.contains(word);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
