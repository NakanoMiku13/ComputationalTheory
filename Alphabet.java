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
                    if(alphabet.size == 0) throw new AlphabetException(5);
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
    public static void main(String[] args) {
        Vector<String> alphabet = createAlphabet();
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
            default: this.message = "Unhandled exception"; break;
        }
        this.message += "Creating default alphabet...\n";
    }
}