package act.mashup.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Result {

	static public final Integer TYPE_LIST = 1;
	static public final Integer TYPE_MAP = 2;

	private boolean status;
	private Date timeStamp;
	private Integer type;
	private String errorMsg;
	private List resultsList;
	private Map resultsMap;

	public Result(Integer type) {
		this.status = true;
		this.timeStamp = new Date();
		this.type = type;
	}

	public void ErrorOccur(String errorMsg) {
		this.status = false;
		this.errorMsg = errorMsg;
	}

	public List GetResultList() {
		return this.resultsList;
	}

	public Map GetResultMap() {
		return this.resultsMap;
	}

	public void SetResultList(List l) {
		this.resultsList = l;
	}

	public void SetResultMap(Map m) {
		this.resultsMap = m;
	}

	public Integer GetType() {
		return this.type;
	}

}
