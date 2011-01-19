package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

public class Rename {
	private Map<Integer, Result> results;
	private Map<String, String> renames;
	private EngineNode en;
	private Integer in;
	private Integer cropLength;
	private ArrayList<Item> items;
	private Result rlt;

	/**
	 * Default constructor.
	 */
	public Rename() {
		rlt = new Result(Result.TYPE_LIST);
		items = new ArrayList<Item>();
	}

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try {
			Prepare();
			DoRename();
		} catch (Exception e) {
			rlt.ErrorOccur("重命名发生错误！");
			e.printStackTrace();
		} finally {
			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while(iterator.hasNext())
			{
				results.put(iterator.next(), rlt);
			}
		}
	}

	// 私有方法

	// 获得所有输入节点
	private void Prepare() {
		in = en.getInputs().get(0);
		renames = en.getParas();
	}

	// 进行重命名
	// ××××××××××××××××××××××警告：指针合并××××××××××××××××××××××××××××××
	private void DoRename() throws Exception {

		items.addAll(results.get(in).GetResultList());
		String _oldname;
		for (int i = 0; i < items.size(); i++) {
			for (Iterator<String> it = renames.keySet().iterator(); it
					.hasNext();) {
				_oldname = it.next();
				if (items.get(i).RenameKey(_oldname, renames.get(_oldname)))
					continue;
				else
					throw new Exception("Rename failed");
			}
		}
		rlt.SetResultList(items);
	}
}
