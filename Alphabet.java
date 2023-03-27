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
    //TRABAJO ERA _________________________________________________________________________________________________________
    //firts point: genrate two languages 
    private static Pair<Vector<String>,Vector<String>> generateLanguages (Vector<String> alphabet){
        int numElements= Integer.parseInt(System.console().readLine("Enter the number of elements or words to generate: "));
        int length = Integer.parseInt(System.console().readLine("Enter the length of the language: "));

        Vector<String> language1 = new Vector<String>();
        Vector<String> language2 = new Vector<String>();

        for(int i=0; i<numElements; i++){
            String word1 = "";
            String word2 = "";
            for(int j = 0, ran1 = 0, ran2 = 0 ; j < length ; j++, ran1 = (int)Math.floor(Math.random()*(alphabet.size())), ran2 = (int)Math.floor(Math.random()*(alphabet.size())), word1 += alphabet.elementAt(ran1),word2 += alphabet.elementAt(ran2));
            language1.add(word1);
            language2.add(word2);
        }
        return new Pair(language1,language2);
    }
    //_________________________________________
    //second point: diference between languages
    private Static Vector<String>{

    }
    //HEEEEEELP c:
    
    
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
    //MAIN
    public static void main(String[] args) {
        //Punto 1 completo
        Vector<String> alphabet = createAlphabet();
        //Punto 2 en desarrollo
        //String str1 = setString(alphabet), str2 = setString(alphabet);

        //Punto 4 en completo    
        Pair<Vector<String>,Vector<String>> languages = generateLanguages(alphabet);
        Vector<String> language1= languages.first, language2= languages.second;
        System.out.println("The firts language generated is: ");
        for ( String i: language1 ) System.out.println ("\t" + i);
        System.out.println("The second language generated is: ");
        for ( String i: language2 ) System.out.println ("\t" + i);

        //Punto 5 en desarrollo
        

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