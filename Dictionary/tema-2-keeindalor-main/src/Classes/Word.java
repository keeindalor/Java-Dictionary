package Classes;
import java.util.ArrayList;

public class Word implements Comparable<Word> {
    public String  word, word_en, type;
    public ArrayList<String> singular = new ArrayList<String>();
    public ArrayList<String> plural = new ArrayList<String>();
    public ArrayList<Definition> definitions = new ArrayList<Definition>();

    @Override
    public String toString() {
        return word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSingular() {
        return singular;
    }

    public void setSingular(ArrayList<String> singular) {
        this.singular = singular;
    }

    public ArrayList<String> getPlural() {
        return plural;
    }

    public void setPlural(ArrayList<String> plural) {
        this.plural = plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public int compareTo(Word o) {
        return CharSequence.compare(this.word, o.getWord());
    }
}
