package act.mashup.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

public class Crop {
	private Map<Integer, Result> results;
	private EngineNode en;
	private Integer in;
	private Integer cropLength;
	private ArrayList<Item> items;
	private Result rlt;

	/**
	 * Default constructor.
	 */
	public Crop() {
		rlt = new Result(Result.TYPE_LIST);
		items = new ArrayList<Item>();
	}

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		this.en = en;
		this.results = results;
		try {
			Prepare();
			DoCrop();
		} catch (Exception e) {
			rlt.ErrorOccur("���з�������");
			e.printStackTrace();
		} finally {
			results.put(en.getId(), rlt);
		}
	}

	// ˽�з���

	// �����������ڵ�
	private void Prepare() {
		in = en.getInputs().get(0);
		cropLength = Integer.getInteger(en.getParas().get("cropLength"));
	}

	// ���вü�
	// �����������������������������������������������棺ָ��ϲ�������������������������������������������������������������
	private void DoCrop() {

		items.addAll(results.get(in).GetResultList());
		for (int i = 0; i < items.size(); i++) {
			if (i >= cropLength)
				items.remove(i);
		}
		rlt.SetResultList(items);
	}

}
