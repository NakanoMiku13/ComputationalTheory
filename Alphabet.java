import java.util.*;
import java.io.*;
public class Alphabet{
    private static Vector<String> createAlphabet(){
        Vector<String> alphabet = new Vector<String>();
        try{
            int option = Integer.parseInt(System.console().readLine("Select an option:\n1) Fill auto\n2) Fill Manual\n"));
            switch(option){
                //Auto
                case 1:{
                    String range = System.console().readLine("Type the range of the alphabet like: a-z, 0-9, a-Z:\n");
                    if(range.length() < 3) throw new AlphabetException(1);
                    if(range.charAt(2) > (char)126) throw new AlphabetException(3);
                    if(range.charAt(0) < (char)33) throw new AlphabetException(4);
                    if(range.charAt(0) > range.charAt(2)) throw new AlphabetException(0);
                    if(range.charAt(1) != '-') throw new AlphabetException(2);
                    String str = "";
                    for(char i = range.charAt(0) ; i <= range.charAt(2) ; str += i++, alphabet.add(str), str = "");
                    if(alphabet.size() == 0) throw new AlphabetException(5);
                }
                break;
                //Manual
                case 2:{
                    int size = Integer.parseInt(System.console().readLine("Type the size of the alphabet (must be higher than 3): "));
                    if(size < 3) throw new AlphabetException(6);
                    for(int i = 0 ; i < size ; i++){
                        String let = System.console().readLine(String.format("Type the Symbol %d: ",i+1));
                        if(let.charAt(0) > (char)126) throw new AlphabetException(3);
                        else if(let.charAt(0) < (char)33) throw new AlphabetException(4);
                        else alphabet.add(let);
                    }
                    if(alphabet.size() == 0) throw new AlphabetException(5);
                }
                break;
                default: break;
            }
        }catch(AlphabetException ex){
            System.out.println(ex.exception());
        }catch(Exception ex){
            System.out.println(ex.toString());
        }finally{
            String str = "";
            if(alphabet.size() == 0) for(char i = 'a' ; i <= 'z' ; str += i++, alphabet.add(str), str += "");
        }
        return alphabet;
    }
    private static Pair<String,String> generateLanguages (Vector<String> alphabet){
        int numElements= Integer.parseInt(System.console().readLine("Enter the number of elements or words to generate: "));
        int length = Integer.parseInt(System.console().readLine("Enter the length of the language: "));

        Vector<String> language1 = new Vector<String>();
        Vector<String> language2 = new Vector<String>();

        for(int i=0; i<numElements; i++){
            String word1 = "";
            String word2 = "";
            for(int j = 0, ran1 = 0, ran2 = 0 ; j < length ; j++, ran1 = 0, ran2 = 0, word1 += alphabet.elementAt(ran1),word2 += alphabet.elementAt(ran2));
        }
        return new Pair(language1,language2);
    }
    private static Boolean verifyString(String string, Map<String,Boolean> alphabet){
        for(int i = 0 ; i < string.length() ; i++){
            if(!alphabet.containsKey(String.format("%c",string.charAt(i)))) return false;
        }
        return true;
    }
    private static String setString(Vector<String> alphabet){
        String string = "";
        Boolean check = false;
        do{
            try{
                string = System.console().readLine("Type a string: ");
                Map<String,Boolean> map = new HashMap<String,Boolean>();
                for(int i = 0 ; i < alphabet.size() ; map.put(alphabet.elementAt(i),true),i++);
                check = verifyString(string, map);
                if(string.length() == 0) throw new AlphabetException(8);
                if(!check) throw new AlphabetException(7);
            }catch(AlphabetException ex){
                System.out.println(ex.exception());
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }while(!check);
        return string;
    }
    private static Vector<String> getPrefixes(String w1, int proper){
        Vector<String> prefixes = new Vector<String>();
        for(int i = 0 ; i < w1.length() ; i++){
            String prefix = "";
            for(int j = 0 ; j < i ; j++){
                prefix += w1.charAt(j);
            }
            prefixes.add(prefix);
        }
        if(proper == 0){
            prefixes.add(w1);
            prefixes.add("lambda");
            return prefixes;

        }else{
            return prefixes;
        }
    }
    private static Vector<String> getSuffixes(String w1, int proper){
        Vector<String> suffixes = new Vector<String>();
        for(int i = w1.length() - 1 ; i > 0 ; i--){
            String suffix = "";
            for(int j =  w1.length() - 1 ; j >= i ; j--){
                suffix += w1.charAt(j);
            }
            suffixes.add(suffix);
        }
        if(proper == 0){
            StringBuilder temp = new StringBuilder();
            temp.append(w1);
            temp.reverse();
            suffixes.add(temp.toString());
            suffixes.add("lambda");
            return suffixes;

        }else{
            StringBuilder temp = new StringBuilder();
            temp.append(w1);
            temp.reverse();
            String t = temp.toString();
            if(!t.equals(w1)){
                suffixes.add(temp.toString());
            }
            return suffixes;
        }
    }
    private static Vector<String> getSubStrings(String w1,Vector<String> prefixes, Vector<String> suffixes, int proper){
        Vector<String> subStrings = new Vector<String>();
        //Deleting the suffixes
        for(int i = 0 , j = 0, pSize = prefixes.size(), sSize = suffixes.size(), cont = 0; cont == 0 ; i++, j++){
            if(i == pSize && j == sSize){
                cont = 1;
            }else{
                String prefix = "", suffix = "", subString = "";
                if(i < pSize) prefix = prefixes.elementAt(i);
                if(j < sSize) suffix = suffixes.elementAt(j);
                if(prefix.length() > 0){
                    for(int k = 0 ; j < w1.length() ; k++){
                        if(prefix.charAt(k) != w1.charAt(k)) subString += w1.charAt(k);
                    }
                }
                if(subString.length() > 0){
                    if(suffix.length() > 0){
                        for(int k = 0 , n = w1.length() - 1; k < suffix.length() ; k++){
                            
                        }
                    }
                }
            }
        }
        return subStrings;
    }
    public static void main(String[] args) {
        //Punto 1 completo
        Vector<String> alphabet = createAlphabet();
        //Punto 2 en desarrollo
        String str1 = setString(alphabet), str2 = setString(alphabet);
        Vector<String> unProperPreffixes = getPrefixes(str1, 0), properPreffixes = getPrefixes(str1,1),unProperSuffixes = getSuffixes(str1, 0), properSuffixes=getSuffixes(str1, 1);
        Vector<String> subStrings = getSubStrings(str1, unProperPreffixes, unProperSuffixes, 0);
        for(String i : subStrings) System.out.println(i);

        //Punto 4 en desarrollo develop
        //Pair<String,String> languages = generateLanguages();
    }
    
}
class Pair<Type1,Type2> {
    public Type1 first;
    public Type2 second;
    public Pair(Type1 first, Type2 second){
        this.first = first;
        this.second = second;
    }
}
class AlphabetException extends Exception{
    public String message;
    public int code = 0;
    public String exception(){
        return String.format("Error: %d\n%s\n",code,message);
    }
    public AlphabetException(int code){
        this.code = code;
        switch(code){
            case 0: this.message = "The initial range can not be higher than the second\n"; break;
            case 1: this.message = "The range must be at least 3 characters\n"; break;
            case 2: this.message = "The range must have '-' in the middle\n"; break;
            case 3: this.message = "The initial part range cant not be higher\n"; break;
            case 4: this.message = "The initial part range cant not be lower\n"; break;
            case 5: this.message = "Can not create correctly the alphabet\n"; break;
            case 6: this.message = "The alphabet size must be higher than 3 symbols\n"; break;
            case 7: this.message = "The string to compare must exist in the alphabet\n"; break;
            case 8: this.message = "The string to compare must have at least one character\n"; break;
            default: this.message = "Unhandled exception"; break;
        }
    }
}