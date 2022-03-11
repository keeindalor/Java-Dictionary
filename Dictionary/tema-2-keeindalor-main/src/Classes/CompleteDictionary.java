package Classes;
import java.util.HashMap;

public class CompleteDictionary {
    public static String language;
    public static HashMap<String, Word> wordsMap = new HashMap<>();

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        CompleteDictionary.language = language;
    }

    public static HashMap<String, Word> getWordsMap() {
        return wordsMap;
    }

    public static void setWordsMap(HashMap<String, Word> wordsMap) {
        CompleteDictionary.wordsMap = wordsMap;
    }

    public static String hashKey (String language, String word) {
        return language + word;
    }

}

