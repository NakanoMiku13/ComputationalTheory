import java.io.*;
import java.util.*;
public class Compiler2{
	private static Boolean isFunction, existMain, existClass;
	public static void main(String[] args){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
			String line;
			int lineCounter = 1;
			while((line=reader.readLine())!=null){
//				System.out.println(line);
				var x= GetError(line, lineCounter++);
				if(!x.first) System.out.println(x.second);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private static Pair<Boolean, String> GetError(String line, int lineNumber) {
		if(line.contains("public class ") || line.contains("class ")){
			if(!line.contains("{")) return new Pair<Boolean, String>(false, String.format("Expected '{' at line: %d : (%d:%d)", lineNumber, lineNumber, line.length()-1));
			else existClass = true;
		}
		if((line.contains("public") || line.contains("private") || line.contains("protected")) && existClass){
			if(line.contains("static")){
				if(line.contains("public static void"){
					if(!existMain && line.contains("main(String[] args){") existMain = true;
				}
				if(line.contains("int") || line.contains("Integer")){
				}else if(line.contains("char"){
				}else if(line.contains("Boolean") || line.contains("bool")){
				}else if(line.contains
			}
		}
		if(line.contains("import ")){
			if(!line.contains(";")) return new Pair<Boolean,String>(false, String.format("Expected a ';' at line: %d : (%d:%d)",lineNumber,lineNumber,line.length()));
 			else if(line.contains(".;")) return new Pair<Boolean,String>(false, String.format("Expected a library/dependencie before ';' at line: %d : (%d:%d)", lineNumber, lineNumber, line.length()-2));
			return new Pair<Boolean, String>(true, "");
		}
		else return new Pair<Boolean, String>(true,"Nothing");
	}
}
class Pair<T1, T2>{
	public T1 first;
	public T2 second;
	public Pair(){ first = (T1)null; second = (T2)null; }
	public Pair(T1 first, T2 second) { this.first = first; this.second = second; }
}
