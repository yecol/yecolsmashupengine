package act.mashup.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.Result;

/**
 * <p>
 * Merge Module
 * </P>
 * 参数：无</br> 返回：内部数据结构Item的列表</br>
 */
public class Merge extends AbstractModule {

	private ArrayList<Integer> ins;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		ins = en.getInputs();
		if (ins.size() == 0)
			throw new IOException();
		for (Iterator it = ins.iterator(); it.hasNext();) {
			Integer index = (Integer) it.next();
			if (this.results.get(index).GetStatus() == 2) {
				it.remove();
			}
		}
		if (ins.size() == 0)
			throw new IOException();
	}

	@Override
	protected void Execute() throws Exception {
		// ××××××××××××××××××××××警告：引用合并××××××××××××××××××××××××××××××
		for (Integer i : ins) {
			items.addAll(results.get(i).GetResultList());
		}
		rlt.SetResultList(items);
	}

	
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
