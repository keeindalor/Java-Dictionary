package Classes;
import java.util.ArrayList;

public class Definition implements Comparable<Definition> {
    public String dict, dictType;
    public int year;
    public ArrayList<String> text = new ArrayList<String>();

    @Override
    public int compareTo (Definition def) {
        return Integer.compare(this.year, def.getYear());
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

}
