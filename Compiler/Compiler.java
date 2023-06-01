import java.io.BufferedReader;
import java.util.*;
import java.io.*;
public class Compiler
	private static Integer _entryPoint;
	public static void main(String[] args){
		try{
			BufferedReader buffer = new BufferedReader(new FileReader(new File(args[0])));
			String line = "";
			while((line = buffer.readLine()) != null){
				//System.out.println(line.replace("\t",""));
				var i = line.replace("\t","").split(" ");
				var x = LineCorrect(i,"D1.json");
				//for(String s : i) System.out.println(s);
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	private static Pair<Boolean, String> LineCorrect(String[] lines, String dictionary){
		Map<String, String> map = GetDictionary(dictionary);
		if(map != null && map.size()>1){
			Pair<Boolean, String> pair = new Pair<Boolean, String>();
			for(String key : lines){
				//System.out.println(line);
				String value = map.get(key);
				System.out.println(key+" "+value);
				if(value == null){

				}else if (value.contains("true")){
					
				}else if(value.contains(".json")){
					String[] ret = new String[lines.length-1];
					for(int i = 1 ; i < lines.length ; i++) ret[i-1] = lines[i];
					for(String lin : ret) System.out.println(lin);
				}
			}
		}
		return new Pair<Boolean, String>();
	}
	private static Pair<Boolean, String> Result(){
		return new Pair<Boolean,String>();
	}
	private static Map<String,String> GetDictionary(String dictionary){
		String dictionariesPath = "Dictionaries/"+dictionary;
		Map<String,String> dictionaryMap = new HashMap<String,String>();
		try{
			File file = new File(dictionariesPath);
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			String line;
			while((line = buffer.readLine()) != null){
				if(line == "{" || line == "}") continue;
				Pair<String,String> pair = GetKeyValue(line);
				dictionaryMap.put(pair.first,pair.second);
			}
		}catch(Exception ex){ System.out.println(ex.getMessage()); }
		return dictionaryMap;
	}
	private static Pair<String,String> GetKeyValue(String line){
		if(line.length() == 1) return new Pair<String,String>();
		String[] lines = line.replace("\t","").replace(" ","").replace("\n","").replace(",","").replace("\"","").split(":");
		return new Pair<String,String>(lines[0],lines[1]);
	}
}
class Pair<T1,T2>{
	public T1 first;
	public T2 second;
	public Pair(T1 first, T2 second){this.first = first; this.second = second; }
	public Pair(){}
}
