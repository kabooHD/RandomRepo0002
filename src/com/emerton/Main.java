package com.emerton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
//TODO: pass code in sonarqube
public class Main {

    public static void main(String[] args) {
        //TODO: make sure the right text is read

        try {
            File myObj = new File("out/production/BentleysChallenge/com/emerton/Tempest.txt");
            Scanner myReader = new Scanner(myObj);
            //HashMap where we will count word occurrences
            Map<String,Integer> cnt = new HashMap<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                cnt = TopWords.top10(data,cnt);
            }
            myReader.close();
            render(cnt);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found " + e.toString());
            e.printStackTrace();
        }
    }

    private static void render(Map<String, Integer> cnt){
        List<String> results_Key;
        List<Integer> results_Value;
        Stream<Map.Entry<String,Integer>> stream = cnt.entrySet().stream().sorted(Main::compare)
                .limit(10);
        results_Key =  stream.map(Map.Entry::getKey).collect(toList());
        Iterator<String> results_KeyIterator = results_Key.iterator();
        stream = cnt.entrySet().stream().sorted(Main::compare)
                .limit(10);
        results_Value =  stream.map(Map.Entry::getValue).collect(toList());
        Iterator<Integer> results_ValueIterator = results_Value.iterator();

        while (results_KeyIterator.hasNext() && results_ValueIterator.hasNext()){
            System.out.println(results_KeyIterator.next() + " ("+results_ValueIterator.next().toString()+")");
        }
    }

    //TODO: Test compare method
    private static int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b) {
        int u = a.getValue(), v = b.getValue();
        return -Integer.compare(u,v);
    }
}
class TopWords {
    //TODO: Comment all lines
    //We make a regex pattern words that may contain hyphen and apostrophe'
    final static private Pattern P = Pattern.compile("[a-z'\\-]+");
    //TODO : make constructor
    public static Map<String,Integer> top10(String s, Map<String,Integer> cnt) {

        s=s.toLowerCase();
        Matcher m = P.matcher(s);
        //For each substring that matches our regex pattern (is a word)
        while (m.find()) {
            //we put/update said substring in our hashmap
            if (m.group().equals("and")  )
                System.out.println(s);
            cnt.put(m.group(),
                    //and if word is already key in hashmap we add 1 to it's value else we initialize it with 0
                    cnt.getOrDefault(
                            m.group(), 0)+1);
        }
        return cnt;
    }

}