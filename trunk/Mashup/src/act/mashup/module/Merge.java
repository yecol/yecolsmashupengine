package act.mashup.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;

/**
 * <p>
 * Merge Module
 * </P>
 * 参数：无</br> 返回：内部数据结构Item的列表</br>
 */
public class Merge {
	private Map<Integer, List> results;
	private EngineNode en;
	private ArrayList<Integer> ins;
	private ArrayList<Item> items;
	private Date timeStamp;

	/**
	 * Default constructor.
	 */
	public Merge() {
		timeStamp = new Date();
		items=new ArrayList<Item>();
	}

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, List> results) {
		this.en = en;
		this.results = results;
		Prepare();
		DoMerge();
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
			items.addAll(results.get(i));
		}
		results.put(en.getId(), items);
	}

	// 打印列表
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
