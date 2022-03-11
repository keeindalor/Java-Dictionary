package ReadFromFile;

import Classes.CompleteDictionary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import Classes.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class FileParsing {
    static File folder = new File("jsonfiles");
    static File[] listOfFiles = folder.listFiles();
    static HashMap<String, Word> CompleteDictionaryHashMap = new HashMap<>();

    /*metoda care construieste un hashmap pe baza listei de cuvinte citite din fisierele json*/
    public static void readFromFile() throws IOException {
        for (File file : listOfFiles) {
            if (file.isFile()) {
                StringBuilder info = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        info.append(line); //citirea informatiilor linie cu linie
                    }
                } catch (IOException e) {
                    System.out.println("IOexception");
                }

                String language = file.getName().split("_")[0]; //limba cuvantului extrasa din numele fisierului

                Gson gson = new Gson();
                Type collectionType = new TypeToken<List<Word>>() {
                }.getType();
                List<Word> words = gson.fromJson(info.toString(), collectionType); //construirea listei de cuvinte
                for (Word w : words) {
                    CompleteDictionaryHashMap.put(CompleteDictionary.hashKey(language, w.word), w); //adaugarea lor in hashmap
                }
                CompleteDictionary.setWordsMap(CompleteDictionaryHashMap);
            }
        }
    }
}
