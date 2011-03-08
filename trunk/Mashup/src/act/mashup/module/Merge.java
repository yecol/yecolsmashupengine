package act.mashup.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Similarity.Text;

/**
 * <p>
 * Merge Module
 * </P>
 * 参数：无</br> 返回：内部数据结构Item的列表</br>
 */
public class Merge extends AbstractModule {

	private ArrayList<Integer> ins;

	private ArrayList<Item> curItems;
	private Item item1;
	private Item item2;
	private ArrayList<ArrayList<Item>> otherResults;
	private Text tarText;
	private Text refText;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		curItems = new ArrayList<Item>();
		otherResults = new ArrayList<ArrayList<Item>>();
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

		for (Integer i : ins) {
			ArrayList al = (ArrayList<Item>) results.get(i).GetResultList();
			otherResults.add((ArrayList<Item>) al.clone());
		}
	}

	@Override
	protected void Execute() throws Exception {
		// ××××××××××××××××××××××警告：引用合并××××××××××××××××××××××××××××××
		int size = otherResults.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				curItems = (ArrayList<Item>) otherResults.get(i).clone();
				int temp1 = 0;
				for (Iterator itOfCurItems = curItems.iterator(); itOfCurItems.hasNext(); temp1++) {
					item1 = (Item) itOfCurItems.next();
					tarText = new Text(item1.getValue("title") + item1.getValue("description"));
					for (int j = i + 1; j < size; j++) {
					    int temp2 = 0;
						for (Iterator itOfOtherRlts = otherResults.get(j).iterator(); itOfOtherRlts.hasNext(); temp2++) {
							item2 = (Item) itOfOtherRlts.next();
							refText = new Text(item2.getValue("title") + item2.getValue("description"));
							double similarity = tarText.ComputeSimilarity(refText);
							//System.out.println("i=" + i + " j=" + j + " temp1=" + temp1 + " temp2=" + temp2 + " simi=" + similarity);
							count++;
							if (similarity > KV.similarityThrashhold){
								itOfOtherRlts.remove();
								item1.addRank();
							}
						}	
					}
					System.out.println("Added i=" + i + " j=" +  " temp1=" + temp1 + " rank="+item1.getRank());
					item1.rankToMap();
					items.add(item1);
				}
			}

		}
		System.out.println(count);
		rlt.SetResultList(items);
	}

	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
