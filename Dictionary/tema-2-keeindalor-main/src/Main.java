import Administration.Administration;
import Classes.Definition;
import Classes.Word;
import ReadFromFile.FileParsing;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        FileParsing.readFromFile();

        Definition definition1 = new Definition();
        Definition definition2 = new Definition();
        Definition definition3 = new Definition();
        Definition definition4 = new Definition();
        Word word1 = new Word();
        Word word2 = new Word();
        Word word3 = new Word();
        Word word4 = new Word();

        definition1.dict = "DEX";
        definition1.dictType = "synonyms";
        definition1.year = 1999;
        definition1.text.add("Tralala");
        definition1.text.add("Tralalalala");
        definition1.text.add("Tralalalalala");

        definition2.dict = "DEX";
        definition2.dictType = "definitions";
        definition2.year = 1900;
        definition2.text.add("lelele");
        definition2.text.add("lelelelele");
        definition2.text.add("lelelelelelele");

        definition3.dict = "Larousse";
        definition3.dictType = "synonyms";
        definition3.year = 1980;
        definition3.text.add("lili");
        definition3.text.add("lilili");
        definition3.text.add("lililili");

        definition4.dict = "Larousse";
        definition4.dictType = "definitions";
        definition4.year = 1979;
        definition4.text.add("lolo");
        definition4.text.add("lololo");
        definition4.text.add("lolololo");

        word1.word = "pisică";
        word1.word_en = "cat";
        word1.type = "noun";
        word1.singular.add("pisică");
        word1.plural.add("pisici");
        word1.definitions.add(definition1);
        word1.definitions.add(definition2);

        word2.word = "pisica";
        word2.word_en = "catt";
        word2.type = "noun";
        word2.singular.add("pisica");
        word2.plural.add("pisici");
        word2.definitions.add(definition2);

        word3.word = "bouche";
        word3.word_en = "buturuga";
        word3.type = "noun";
        word3.singular.add("bouche");
        word3.plural.add("bouches");
        word3.definitions.add(definition3);

        word4.word = "aller";
        word4.word_en = "walk";
        word4.type = "verb";
        word4.singular.add("alle");
        word4.singular.add("alles");
        word4.singular.add("alle");
        word4.plural.add("allons");
        word4.plural.add("allez");
        word4.plural.add("allent");


        System.out.println("Apelul metodei 2");
        if (Administration.addWord(word1, "ro")) {
            System.out.println("Cuvantul " + word1.word + " a fost adaugat cu succes");
        } else {
            System.out.println("Cuvantul " + word1.word + " exista deja in dictionar");
        }

        if (Administration.addWord(word3, "fr")) {
            System.out.println("Cuvantul " + word3.word + " a fost adaugat cu succes");
        } else {
            System.out.println("Cuvantul " + word3.word + " exista deja in dictionar");
        }

        System.out.println("Apelul metodei 3");
        if (Administration.removeWord(word1.word, "ro")) {
            System.out.println("Cuvantul " + word1.word + " a fost eliminat cu succes");
        } else {
            System.out.println("Cuvantul " + word1.word + " nu exista in dictionar");
        }
        if (Administration.removeWord(word2.word, "ro")) {
            System.out.println("Cuvantul " + word2.word + " a fost eliminat cu succes");
        } else {
            System.out.println("Cuvantul " + word2.word + " nu exista in dictionar");
        }
        //adaug cuvantul inapoi pentru a continua testele
        if (Administration.addWord(word1, "ro")) {
            System.out.println("Cuvantul " + word1.word + " a fost adaugat cu succes");
        }

        System.out.println("Apelul metodei 4");
        if (Administration.addDefinitionForWords(word1.word, "ro", definition4)) {
            System.out.println("S-a adaugat definitia " + definition1.text.get(0) + " pentru cuvantul " + word1.word);
        } else {
            System.out.println("nu s-a adaugat definitia pentru ca nu s-a gasit cuvantul sau exista deja");
        }
        if (Administration.addDefinitionForWords(word1.word, "ro", definition4)) {
            System.out.println("S-a adaugat definitia " + definition1.text.get(0) + " pentru cuvantul " + word1.word);
        } else {
            System.out.println("nu s-a adaugat definitia pentru ca nu s-a gasit cuvantul sau exista deja");
        }

        System.out.println("Apelul metodei 5");
        if (Administration.removeDefinition("pisicuta", "ro", "Dicționar de sinonime")) {
            System.out.println("S-a eliminat definitia din dictionarul Dicționar de sinonime");
        } else {
            System.out.println("Cuvantul nu exista sau dictionarul nu exista");
        }
        if (Administration.removeDefinition(word1.word, "ro", "DEX")) {
            System.out.println("S-a eliminat definitia din dictionarul Dicționar de sinonime");
        } else {
            System.out.println("Cuvantul nu exista sau dictionarul nu exista");
        }

        System.out.println("Apelul metodei 6");
        if (Administration.translateWord("pisică", "ro", "fr").equals("Doesn't exist")) {
            System.out.println("Cuvantul nu se poate traduce");
        } else {
            System.out.println(Administration.translateWord("pisică", "ro", "fr"));
        }
        if (Administration.translateWord("pisicul", "ro", "fr").equals("Doesn't exist")) {
            System.out.println("Cuvantul pisicul nu se poate traduce");
        } else {
            System.out.println(Administration.translateWord("pisicul", "ro", "fr"));
        }

        System.out.println("Apelul metodei 7");
        Administration.addWord(word4, "fr");
        System.out.println(Administration.translateSentence("pisici merg", "ro", "fr"));
        System.out.println(Administration.translateSentence("chats jeu", "fr", "ro"));

        System.out.println("Apelul metodei 8");
        Administration.addWord(word2, "ro");
        ArrayList<String> list1 = Administration.translateSentences("pisică pisica", "ro", "fr");
        for (String s : list1) {
            System.out.println(s);
        }
        ArrayList<String> list2 = Administration.translateSentences("pisică câine", "ro", "fr");
        for (String s : list2) {
            System.out.println(s);
        }

        System.out.println("Apelul metodei 9");
        ArrayList<Definition> def1 = Administration.getDefinitionsForWord("merge", "ro");
        ArrayList<Definition> def2 = Administration.getDefinitionsForWord("lalala", "ro");
        for (Definition d : def1) {
            System.out.println("Anul aparitiei: " + d.year);
            for (String s : d.text) {
                System.out.println(s);
            }
        }
        for (Definition d : def2) {
            System.out.println("Anul aparitiei: " + d.year);
            for (String s : d.text) {
                System.out.println(s);
            }
        }

        System.out.println("Apelul metodei 10");
        Administration.exportCompleteDictionary("ro");
        Administration.exportCompleteDictionary("ru");




    }
}
