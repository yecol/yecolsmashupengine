package act.mashup.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

/**
 * <p>
 * Merge Module
 * </P>
 * 参数：无</br> 返回：内部数据结构Item的列表</br>
 */
public class Merge {
	private Map<Integer, Result> results;
	private EngineNode en;
	private ArrayList<Integer> ins;
	private ArrayList<Item> items;
	private Date timeStamp;
	private Result rlt;


	/**
	 * Default constructor.
	 */
	public Merge() {
		timeStamp = new Date();
		rlt = new Result(Result.TYPE_LIST);
		items=new ArrayList<Item>();
	}

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try{
		   Prepare();
		   DoMerge();
		}catch(Exception e){
		   rlt.ErrorOccur("合并发生错误！");
		}finally {
			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while(iterator.hasNext())
			{
				results.put(iterator.next(), rlt);
			}
		}
	}

	// 私有方法

	//获得所有输入节点
	private void Prepare() {
		ins = en.getInputs();
	}

	//进行合并
	//××××××××××××××××××××××警告：指针合并××××××××××××××××××××××××××××××
	private void DoMerge() {
		for(Integer i:ins){
			items.addAll(results.get(i).GetResultList());
		}
		rlt.SetResultList(items);
	}

	// 打印列表
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
