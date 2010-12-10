package act.mashup.util;

import java.util.HashMap;
import java.util.Map;

public class Item {

	private Map<String,String> KVMap;
	
	public Item() {
		super();
		KVMap=new HashMap<String,String>();
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
