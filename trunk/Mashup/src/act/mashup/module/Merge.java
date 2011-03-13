package act.mashup.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Log;
import act.mashup.util.Similarity.SimilarityDetector;
import act.mashup.util.Similarity.Text;

/**
 * <p>
 * Merge Module
 * </P>
 * 参数：无</br> 返回：内部数据结构Item的列表</br>
 */
public class Merge extends AbstractListModule {

	private ArrayList<Integer> ins;
	private String removeDuplications;

	private ArrayList<Item> curItems;
	private Item item1;
	private Item item2;
	private ArrayList<ArrayList<Item>> otherResults;
	private Text tarText;
	private Text refText;
	private ArrayList<Item> al;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		curItems = new ArrayList<Item>();
		otherResults = new ArrayList<ArrayList<Item>>();
		ins = en.getInputs();
		removeDuplications = en.getParas().getChildTextTrim("removeDuplications", KV.gf);
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

		for (Integer i : ins) {
			al = (ArrayList<Item>) results.get(i).GetResultList();
			ArrayList<Item> tempArrayList = (ArrayList<Item>) al.clone();
			items.addAll(tempArrayList);
		}
	}

	@Override
	protected void Execute() throws Exception {
		// ××××××××××××××××××××××警告：引用合并××××××××××××××××××××××××××××××
		if (removeDuplications.equals("1")) {

			SimilarityDetector sd = new SimilarityDetector((ArrayList<Item>) items);
			sd.Detect();
		}

		// Log.logger.debug("Compare " + count + " times.");
		rlt.SetResultList(items);
	}

}
