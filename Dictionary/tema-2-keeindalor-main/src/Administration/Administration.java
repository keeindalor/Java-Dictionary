package Administration;

import Classes.CompleteDictionary;
import Classes.Definition;
import Classes.Word;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Administration {
    /*functia care adauga un cuvant*/
    public static boolean addWord(Word word, String language) {
        String hash = CompleteDictionary.hashKey(language, word.word);
        if (CompleteDictionary.wordsMap.get(hash) == null) { //verific daca nu exista cuvantul
            CompleteDictionary.wordsMap.put(hash, word); //il adaug
            return true;
        }
        return false; //exista deja
    }
    /*functia care elimina un cuvant*/
    public static boolean removeWord(String word, String language) {
        String hash = CompleteDictionary.hashKey(language, word);
        if (CompleteDictionary.wordsMap.get(hash) != null) { //verific daca exista cuvantul
            CompleteDictionary.wordsMap.remove(hash); //il elimin
            return true;
        }
        return false; //nu exista
    }
    /*functia care adauga definitii pentru un cuvant*/
    public static boolean addDefinitionForWords(String word, String language, Definition definitions) {
        String hash = CompleteDictionary.hashKey(language, word);
        if (CompleteDictionary.wordsMap.get(hash) == null)
            return false; //cazul pentru inexistenta cuvantului
        for (Definition d : CompleteDictionary.wordsMap.get(hash).definitions) {
            if (d.dict.equals(definitions.dict)) //daca exista definitia, nu o adaug
                return false;
        }
        CompleteDictionary.wordsMap.get(hash).definitions.add(definitions); //adaug daca nu exista
        return true;
    }
    /*functia care elimina definitia dintr-un dictionar*/
    public static boolean removeDefinition(String word, String language, String dictionary) {
        String hash = CompleteDictionary.hashKey(language, word);
        if (CompleteDictionary.wordsMap.get(hash) == null) {
            return false; //cazul pentru inexistenta cuvantului
        }
        for (Definition d : CompleteDictionary.wordsMap.get(hash).definitions) {
            if (d.dict.equals(dictionary)) {
                if (d.dictType.equals("definitions")) {
                    CompleteDictionary.wordsMap.get(hash).definitions.remove(d); //daca exista definitia, o elimin
                    return true;
                }
            }
        }
        return false; //nu exista
    }
    /*functie care traduce un cuvant dintr-o limba in alta */
    public static String translateWord(String word, String fromLanguage, String toLanguage) {
        String englishWord;
        String hash = CompleteDictionary.hashKey(fromLanguage, word);
        if (CompleteDictionary.wordsMap.get(hash) == null) { //verific daca cuvantul nu exista
            return "doesn't exist";
        }
        englishWord = CompleteDictionary.wordsMap.get(hash).word_en; //daca exista, il iau pe cel in engleza

        for (Map.Entry<String, Word> hashMap : CompleteDictionary.wordsMap.entrySet()) { //parcurg hashmap-ul
            if (hashMap.getValue().word_en.equals(englishWord) && hashMap.getKey().contains(toLanguage)) {
                return hashMap.getValue().word; //verific daca este acelasi cuvant in engleza, si hash-ul contine
                // limba in care dorim sa traducem
            }
        }
        return "doesn't exist";
    }

    /*functia care traduce o propozitie dintr-o limba in alta*/
    public static String translateSentence(String sentence, String fromLanguage, String toLanguage) {
        String[] words = sentence.split(" "); //impart propozitia de tradus intr-un array de string-uri
        String raw_word;
        String translated_word;
        StringBuilder translated = new StringBuilder();
        String hash;
        boolean check = false; //check este pentru verbele care au aceeasi conjugare (ex: pers 1 sg cu pers 3 pl)
        int count;  //folosesc count pentru a lua variantele de singular sau plural fie pt subst, fie pt conjugarile vb
        for (String s : words) { //parcurg cuvant cu cuvant
            for (Map.Entry<String, Word> hashMap : CompleteDictionary.wordsMap.entrySet()) { //parcurg hashmap
                count = 0;
                for (String string : hashMap.getValue().singular) { //parcurg lista de singulare
                    count++;
                    if (string.equals(s)) { //vf daca cuvantul din propozitie se regaseste in lista de singulare
                        raw_word = hashMap.getValue().word; //luam cuvantul de baza (de ex, daca este vb luam infinitiv)
                        if (translateWord(raw_word, fromLanguage, toLanguage).equals("doesn't exist")) {
                            translated.append(s); //daca nu exista cuvantul, il lasam netradus
                            translated.append(" ");
                            check = true;
                            break;
                        }
                        translated_word = translateWord(raw_word, fromLanguage, toLanguage); //traducem cuvantul de baza
                        hash = CompleteDictionary.hashKey(toLanguage, translated_word); //calculam hash-ul
                        translated.append(CompleteDictionary.wordsMap.get(hash).singular.get(count - 1)); //luam din sg
                        translated.append(" ");
                        check = true;
                        break;
                    }
                }
                if (check)
                    continue;
                count = 0;
                for (String string : hashMap.getValue().plural) { //procedez la fel si pentru plural, la fel ca pt sg.
                    count++;
                    if (string.equals(s)) {
                        raw_word = hashMap.getValue().word;
                        if (translateWord(raw_word, fromLanguage, toLanguage).equals("doesn't exist")) {
                            translated.append(s);
                            translated.append(" ");
                            break;
                        }
                        translated_word = translateWord(raw_word, fromLanguage, toLanguage);
                        hash = CompleteDictionary.hashKey(toLanguage, translated_word);
                        translated.append(CompleteDictionary.wordsMap.get(hash).plural.get(count - 1));
                        translated.append(" ");
                        break;
                    }
                }
            }
        }
        return translated.toString(); //returnam propozitia tradusa
    }

    /* functia care traduce o propozitie si returneaza o lista cu maxim 3 traduceri folosind sinonime*/
    public static ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage) throws
            RuntimeException {
        ArrayList<String> translations = new ArrayList<>();
        String[] words = sentence.split(" "); //delimitez string-ul intr-un array de string-uri
        String translated_word;
        String raw_word, hash;
        StringBuilder translated = new StringBuilder(translateSentence(sentence, fromLanguage, toLanguage)); //prima traducere din lista
        boolean check = true; //folosesc check pentru a nu iesi din dimensiunea listei de sinonime
        translations.add(translated.toString()); //adaug prima traducere in lista
        for (int i = 0; i < 2 && check; i++) { //repet procedeul pentru maxim inca doua sinonime
            translated = new StringBuilder();
            for (String s : words) {
                if (translateWord(s, fromLanguage, toLanguage).equals("doesn't exist")) {
                    translated.append(s);
                    translated.append(" ");
                    continue;
                }
                translated_word = translateWord(s, fromLanguage, toLanguage); //traduc cuvantul
                for (Map.Entry<String, Word> hashMap : CompleteDictionary.wordsMap.entrySet()) { //parcurg hash
                    if (translated_word.equals(hashMap.getValue().word)) { //daca se afla in hash, parcurg lista definitii
                        for (Definition definitions : hashMap.getValue().definitions) {
                            if (definitions.dictType.equals("synonyms")) { //caut lista de sinonime
                                if (i == definitions.text.size()) {
                                    check = false;
                                    break;
                                }
                                raw_word = definitions.text.get(i); //iau sinonimul
                                hash = CompleteDictionary.hashKey(toLanguage, raw_word);//calculez hash pentru acesta
                                if (CompleteDictionary.wordsMap.get(hash) != null) {
                                    if (CompleteDictionary.wordsMap.get(hash).type.equals("verb")) {
                                        //daca exista cuvantul in hash si e verb, voi lua varianta de sg pentru simplitate
                                        translated.append(CompleteDictionary.wordsMap.get(hash).singular.get(0));
                                        translated.append(" ");
                                    }
                                } else {
                                    translated.append(raw_word); //daca nu e verb, aceea este forma (elem i din lista de sin.)
                                    translated.append(" ");
                                }
                            }
                        }
                    }
                }
                if (!check) //daca depasesc dimensiunea, ma opresc din a traduce
                    break;
            }
            translations.add(translated.toString()); //adaug in lista
        }
        return translations; //returnez lista de traduceri
    }

    /*functie care returneaza o lista de definitii ordonate crescator pentru un cuvant*/
    public static ArrayList<Definition> getDefinitionsForWord(String word, String language) {
        ArrayList<Definition> sortedDefinitions = new ArrayList<>();
        String hash = CompleteDictionary.hashKey(language, word);//calculez hash-ul cuvantului
        if (CompleteDictionary.wordsMap.get(hash) != null){
            sortedDefinitions = CompleteDictionary.wordsMap.get(hash).definitions; //adaug lista de definitii
            Collections.sort(sortedDefinitions); //o ordonez
            return sortedDefinitions; // o returnez
        }
        System.out.println("nu exista cuvantul " + word);
        return sortedDefinitions;
    }

    /*functie care exporta dictionarul din obiecte in format JSON*/
    public static void exportCompleteDictionary(String language) {
        ArrayList<Word> words = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //creez un obiect GSON
        String infoInJSON;
        for (Map.Entry<String, Word> hashMap : CompleteDictionary.wordsMap.entrySet()) {
            if (hashMap.getKey().contains(language))
                words.add(hashMap.getValue()); //parcurg hash si adaug toate cuvintele in lista
        }
        Collections.sort(words); //sortarea dupa cuvinte
        if (words.isEmpty()) {
            System.out.println("Nu exista limba");
            return;
        }
        for (Word word : words) {
            Collections.sort(word.definitions); //sortare definitii dupa an
        }
        File file = new File("src/" + language + "_dict.json"); //creez fisierul
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Cannot crete file");
        }
        infoInJSON = gson.toJson(words);
        try {
            FileWriter write = new FileWriter(file); //scriu in fisiere
            write.write(infoInJSON);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
