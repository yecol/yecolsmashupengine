package act.mashup.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

public class Sort {
	private Map<Integer, Result> results;
	private EngineNode en;
	private Result rlt;
	private Comparator comparator;

	/**
	 * Default constructor.
	 */
	public Sort() {
		rlt = new Result(Result.TYPE_LIST);
	}

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		this.en = en;
		this.results = results;
		try {
			Prepare();
			DoSort();
		} catch (Exception e) {
			rlt.ErrorOccur("����������");
			e.printStackTrace();
		} finally {
			results.put(en.getId(), rlt);
		}
	}

	// ˽�з���

	// ��ò���������˳����������������½��Ƚ���
	private void Prepare() {
		String sortKind = en.getParas().get("sortKind").trim();
		String sortKey = en.getParas().get("sortKey");
		//Ĭ���½�˳��Ƚ���
		if(sortKind!=null&&sortKind.equals("Decrease")){
		   comparator = new DecComparator(sortKey);
		}else{
		   comparator = new IncComparator(sortKey);
		}
	}

	// ��������
	private void DoSort() {
		Integer in = en.getInputs().get(0);
		List temp = results.get(in).GetResultList();
		Collections.sort(temp, comparator);
		rlt.SetResultList(temp);
	}

	//˳��Ƚ�����
	private class IncComparator implements Comparator {
		private String sortKey;

		public IncComparator(String sortKey) {
			this.sortKey = sortKey;
		}

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			Item i1 = (Item) o1;
			Item i2 = (Item) o2;
			return i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
		}
	}

	//����Ƚ�����
	private class DecComparator implements Comparator {
		private String sortKey;

		public DecComparator(String sortKey) {
			this.sortKey = sortKey;
		}

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			Item i1 = (Item) o1;
			Item i2 = (Item) o2;
			return -i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
		}
	}

}
