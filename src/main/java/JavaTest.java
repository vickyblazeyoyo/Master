import java.util.HashMap;
import java.util.Map;

public class JavaTest {

	
	public static void main(String[] args) {
		Map<Integer,String> hash= new HashMap<Integer,String>();
		Integer[] keys= {1,2,3,4,5};
		String [] values= {"one","two","three","four","five"};
		
		for (int i = 0; i < keys.length; i++) {
	         Integer key=keys[i];
	         String value=values[i];
	         hash.put(key, value);
		}
		System.out.println(hash.get(4));
	
	}
}

