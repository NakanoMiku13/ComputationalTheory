import java.io.*;
import java.util.*;
public class Compiler2{
	private static Boolean isFunction, existMain, existClass;
	public static void main(String[] args){
		try{
			VerifyName("public class Compiler");
			VerifyName("public static int putor;");
			VerifyName("public static int putor = 12;");
			BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
			String line;
			int lineCounter = 1;
			while((line=reader.readLine())==null){
//				System.out.println(line);
				var x= GetError(line, lineCounter++);
				if(!x.first) System.out.println(x.second);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private static Boolean IsReserved(String name){
		return ( name.contains("static") || name.contains("class") || name.contains("public") || name.contains("private") || name.contains("protected") || name.contains("int") || name.contains("float") || name.contains("double") || name.contains("char") || name.contains("void") || name.contains("Boolean") || name.contains("short") || name.contains("long")) ;
	}
	private static Boolean IsSymbol(String line){
		return (line.equals("=") || line.equals(";") );
	}
	static Pair<Boolean,String> VerifyName(String line){
		if(!line.contains("=")){
			String[] splitted = line.split(" ");
			String name = "";
			for(String spi : splitted ){
				String sp = spi.replace(";","");
				if(!IsReserved(sp) && !IsSymbol(sp)) System.out.println(sp);
			}
		}else{
			String name = line;
			if(name.contains("static")) line = line.replace("static","");
			if(name.contains("class")) line = line.replace("class","");
			if(name.contains("public")) line = line.replace("public","");
			if(name.contains("private")) line = line.replace("private","");
			if(name.contains("protected")) line = line.replace("protected","");
			if(name.contains("int")) line = line.replace("int","");
			if(name.contains("float")) line = line.replace("float","");
			if(name.contains("double")) line = line.replace("double","");
			if(name.contains("char")) line = line.replace("char","");
			if(name.contains("void")) line = line.replace("void","");
			if(name.contains("Boolean")) line = line.replace("Boolean","");
			if(name.contains("short")) line = line.replace("short","");
			if(name.contains("long")) line = line.replace("long","");
			line = line.replace(" ","");
			line = line.replace("(","");
			line = line.replace(")","");
			line = line.replace("{","");
			line = line.replace("}","");
			line = line.replace("[","");
			line = line.replace("}","");
			System.out.println(line);
		}
		return null;
	}
	private static Pair<Boolean,String> DataTypes(String line, int lineNumber){
		String reason = "";
		Boolean status = true;
		if(line.contains("int ") || line.contains("Integer ")){
			if(line.contains("=;")){
				status = false;
				reason = String.format("Expected a value before ';' at line: %d : (%d:%d)",lineNumber,lineNumber,line.length()-1);
			}else if(line.contains("int ;")){
				status = false;
				reason = String.format("Expected a name before ';' at line: %d : (%d:%d)",lineNumber,lineNumber,line.length()-1);
			}else if(line.contains("=")){

			}else if(line.contains(";")){

			}
			//public static int a; public static int Function(){}
		}else if(line.contains("char ")){

		}else if(line.contains("Boolean ") || line.contains("bool ")){

		}else if(line.contains("String ")){

		}else if(line.contains("float ") || line.contains("double ")){

		}
		return new Pair<Boolean,String>(status,reason);
	}
	private static Pair<Boolean, String> GetError(String line, int lineNumber) {
		if(line.contains("public class ") || line.contains("class ")){
			if(!line.contains("{")) return new Pair<Boolean, String>(false, String.format("Expected '{' at line: %d : (%d:%d)", lineNumber, lineNumber, line.length()-1));
			else existClass = true;
		}
		if((line.contains("public ") || line.contains("private ") || line.contains("protected ")) && existClass){
			if(line.contains(" static ")){
				if(line.contains("public static void ")){
					if(!existMain && line.contains("main(String[] args){")) existMain = true;
				}
				
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
