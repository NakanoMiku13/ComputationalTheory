import java.util.*;
import java.io.*;
public class Compiler{
	private static Map<String,String> dicts;
	private static Pair<String,String> GetKeyAndValue;
	public static void main(String[] args){
		dicts = new HashMap<String, String>();
		System.out.println("Hello");
		String dictionariesPath = "Dictionaries/";
		File[] files = new File(dictionariesPath).listFiles();
		for(File file : files){
			try{
				FileReader reader = new FileReader(file);
				BufferedReader buffer = new BufferedReader(reader);
				String line;
				while((line = buffer.readLine()) != null){
					System.out.println(line);
				}
			}catch(Exception ex){}
		}
	}
}
class Pair<T1,T2>{
	public T1 first;
	public T2 second;
	public Pair(T1 first, T2 second){this.first = first; this.second = second; }
}
