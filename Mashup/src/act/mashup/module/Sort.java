package act.mashup.module;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try {
			Prepare();
			DoSort();
		} catch (Exception e) {
			rlt.ErrorOccur("����������");
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

	// ˽�з���

	// ��ò���������˳����������������½��Ƚ���
	private void Prepare() throws IOException {
		String sortKind = en.getParas().get("sortKind").trim();
		String sortKey = en.getParas().get("sortKey");
		if(sortKey==null||sortKey.length()==0)
			throw new IOException();
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
			Collator collator=Collator.getInstance(Locale.CHINA);
			return collator.compare(i1.getValue(sortKey), i2.getValue(sortKey));
			
			//return i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
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

			Collator collator=Collator.getInstance(Locale.CHINA);
			return -collator.compare(i1.getValue(sortKey), i2.getValue(sortKey));
			
			//return -i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
		}
	}

}