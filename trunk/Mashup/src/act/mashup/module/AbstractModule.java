package act.mashup.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.Result;

public abstract class AbstractModule {

	protected Map<Integer, Result> results;
	protected EngineNode en;
	protected Date timeStamp,debugTime;
	protected List<Item> items;
	protected Result rlt;

	/**
	 * Default constructor.
	 */
	public AbstractModule() {
		timeStamp = new Date();
		rlt = new Result(Result.TYPE_LIST);
		items = new ArrayList<Item>();
	}

	// ��Engine���õķ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try {
			Prepare();
			Execute();
			debugTime=new Date();
			System.out.println("This Module has runned "+(debugTime.getTime()-timeStamp.getTime())+"ms.");
		} catch (Exception e) {
			e.printStackTrace();
			rlt.ErrorOccur(e.getMessage());
		} finally {
			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while (iterator.hasNext()) {
				results.put(iterator.next(), rlt);
			}
		}

	}

	// ׼���׶�
	protected abstract void Prepare() throws Exception;

	// ���н׶�
	protected abstract void Execute() throws Exception;

}
