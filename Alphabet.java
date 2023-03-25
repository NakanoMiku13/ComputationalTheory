import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static Vector<String> getSubStrings(String w1,Vector<String> prefixes, Vector<String> suffixes){
        Vector<String> subStrings = new Vector<String>();
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        for(int i = 0 ; i < prefixes.size() ; i ++){
            String prefix = prefixes.elementAt(i);
            int k = 0;
            for(; k < prefix.length() && k < w1.length() ; k++)
                    if(prefix.charAt(k) == w1.charAt(k)) continue;
                    else break;
            for(int j = 0 ; j < suffixes.size() ; j++){
                String subString = "",suffix = suffixes.elementAt(j);
                int m = w1.length() - 1;
                for(int n = 0; m > 0 && n < suffix.length() ; m--, n++)
                    if(suffix.charAt(n) == w1.charAt(m)) continue;
                    else break;
                if(k < prefix.length()) k = 0;
                if(m > prefix.length()) m = w1.length();
                for(int n = k ; n < m ; n++) subString += w1.charAt(n);
                if(!map.containsKey(subString) && !subString.equals("") && subString.length() > 0){ subStrings.add(subString); if(!subString.equals("") && subString.length() > 0) map.put(subString, true);}
            }
        }
        return subStrings;
    }
    private static Vector<String>  getSubSequences(String w1){
        Vector<String> subSequences = new Vector<String>();
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        Map<Integer,Boolean> map2 = new HashMap<Integer,Boolean>();
        for(int i = 0, cant = 0 ; i < w1.length() ; i++, cant = (int)Math.floor(Math.random()*(w1.length()) + 1)){
            String subSequence = "";
            int pos = (int)Math.floor(Math.random() * (w1.length() - 1) + 1);
            while(cant > 0){
                if(!map2.containsKey(pos)){
                    subSequence += w1.charAt(pos);
                    map2.put(pos, true);
                }
                pos = (int)Math.floor(Math.random() * (w1.length() - 1) + 1);
                cant --;
            }
            if(!map.containsKey(subSequence) && !subSequence.equals("") && subSequence.length() > 0){ subSequences.add(subSequence); map.put(subSequence, true); }
        }
        return subSequences;
    }
    private static Vector<String> existPrefixes(Vector<String> prefixesI, String w1){
        Vector<String> prefixes = new Vector<String>();
        for(String prefix : prefixesI){
            Boolean temp = true;
            for(int i = 0 ; i < prefix.length(); i++){
                if(prefix.charAt(i) == w1.charAt(i)) continue;
                else{
                    temp = false;
                    break;
                }
            }
            if(temp) prefixes.add(prefix);
        }
        return prefixes;
    }
    private static Vector<String> existSuffixes(Vector<String> suffixesI,String w1){
        Vector<String> suffixes = new Vector<String>();
        for(String suffix : suffixesI){
            Boolean temp = true;
            for(int i = 0 ; i < suffix.length() ; i++){
                if(suffix.charAt(i) == w1.charAt(i)) continue;
                else{
                    temp = false;
                    break;
                }
            }
            if(temp) suffixes.add(suffix);
        }
        return suffixes;
    }
    private static void regularExpression(){
        Boolean matchFound = false;
        do{
            try{
                String expression = System.console().readLine("Type a string with at least one number repeated: ");
                if(expression.length() < 5) throw new AlphabetException(9);
                else{
                    for(int i = 0 ; i < 10 && !matchFound; i++){
                        try{
                            Pattern pattern = Pattern.compile(String.format("[0-9]*?%d[0-9]*?%d[0-9]*?",i,i));
                            Matcher matcher = pattern.matcher(expression);
                            matchFound = matcher.find();
                            if(matchFound) System.out.println("The expression is correct");
                        }catch(Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }catch(AlphabetException exception){
                System.out.println(exception.exception());
            }
        }while(!matchFound);
    }
    public static void main(String[] args) {
        Vector<String> alphabet = createAlphabet();
        String str1 = setString(alphabet), str2 = setString(alphabet);
        Vector<String> unProperPrefixes = existPrefixes(getPrefixes(str1, 0),str2),
        properPrefixes = existPrefixes(getPrefixes(str1,1),str2),
        unProperSuffixes = existSuffixes(getSuffixes(str1, 0),str2),
        properSuffixes = existSuffixes(getSuffixes(str1, 1),str2),
        subStrings = getSubStrings(str2, unProperPrefixes, unProperSuffixes),
        subSequences = getSubSequences(str2) ;
        System.out.println("Un-proper Prefixes:");
        for(String prefix: unProperPrefixes) System.out.println(prefix);
        System.out.println("Proper Prefixes:");
        for(String prefix: properPrefixes) System.out.println(prefix);
        System.out.println("Un-proper Suffixes:");
        for(String suffix: unProperSuffixes) System.out.println(suffix);
        System.out.println("Proper Suffixes:");
        for(String suffix: properSuffixes) System.out.println(suffix);
        System.out.println("Substrings:");
        for(String subString: subStrings) System.out.println(subString);
        System.out.println("SubSequence:");
        for(String subSequence: subSequences) System.out.println(subSequence);
        regularExpression();
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
            case 9: this.message = "The string to compare must have at least 5 characters\n"; break;
            default: this.message = "Unhandled exception"; break;
        }
    }
}