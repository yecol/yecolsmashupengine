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

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		ArrayList<Integer> outputs;
		Iterator<Integer> iterator;
		this.en = en;
		this.results = results;
		try {
			Prepare();
			DoSort();
		} catch (Exception e) {
			rlt.ErrorOccur("排序发生错误！");
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

	// 获得参数，根据顺序排序或逆序排序新建比较器
	private void Prepare() throws IOException {
		String sortKind = en.getParas().get("sortKind").trim();
		String sortKey = en.getParas().get("sortKey");
		if(sortKey==null||sortKey.length()==0)
			throw new IOException();
		//默认新建顺序比较器
		if(sortKind!=null&&sortKind.equals("Decrease")){
		   comparator = new DecComparator(sortKey);
		}else{
		   comparator = new IncComparator(sortKey);
		}
	}

	// 进行排序
	private void DoSort() {
		Integer in = en.getInputs().get(0);
		List temp = results.get(in).GetResultList();
		Collections.sort(temp, comparator);
		rlt.SetResultList(temp);
	}
	

	//顺序比较器类
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

	//逆序比较器类
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
