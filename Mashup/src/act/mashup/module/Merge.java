package act.mashup.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.Result;
import act.mashup.util.Similarity.SimilarityDetector;

/**
 * <p>
 * Merge Module
 * </P>
 * ��������</br> ���أ��ڲ����ݽṹItem���б�</br>
 */
public class Merge extends AbstractModule {

	private ArrayList<Integer> ins;

	private ArrayList<Item> curItems;

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		curItems = new ArrayList<Item>();
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
			curItems.addAll((ArrayList<Item>) al.clone());
		}
	}

	@Override
	protected void Execute() throws Exception {

		SimilarityDetector sd = new SimilarityDetector(curItems);
		sd.Detect();
		Collections.sort(curItems);
		rlt.SetResultList(curItems);
	}

}
