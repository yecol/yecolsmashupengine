package act.mashup.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Result {
	
	static public final Integer TYPE_LIST = 1;
	static public final Integer TYPE_MAP =2;

	private boolean status;
	private Date timeStamp;
	private Integer type;
	private String errorMsg;
	private List resultsList;
	private Map resultsMap;
	
	public Result(Integer type){
		status=true;
		timeStamp=new Date();
		
	}
}
