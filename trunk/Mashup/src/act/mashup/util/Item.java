package act.mashup.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Item {

	private Map<String,String> KVMap;
	
	public Item() {
		super();
		KVMap=new HashMap<String,String>();
	}
	
	public Set<String> getKeys(){
		return KVMap.keySet();
	}
	
	public String getValue(String key){
		return KVMap.get(key);		
	}
	
	public void setValue(String key,String value){
		KVMap.put(key, value);
	}
	
	public String toString(){
		return KVMap.toString();		
	}

}
