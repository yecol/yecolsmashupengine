package act.mashup.module;

import java.io.IOException;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Sort extends AbstractModule {

	private Comparator comparator;
	private Integer in;
	
	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	
	// 获得参数，根据顺序排序或逆序排序新建比较器
	@Override
	protected void Prepare() throws Exception {
		
		String sortKind = en.getParas().getChildTextTrim("sortKind",KV.gf);
		String sortKey = en.getParas().getChildTextTrim("sortKey",KV.gf);
		if (sortKey == null || sortKey.length() == 0)
			throw new IOException();
		// 默认新建顺序比较器
		if (sortKind != null && sortKind.equals("Decrease")) {
			comparator = new DecComparator(sortKey);
		} else {
			comparator = new IncComparator(sortKey);
		}

	}

	// 进行排序
	@Override
	protected void Execute() throws Exception {
		in = en.getInputs().get(0);
		List temp = results.get(in).GetResultList();
		Collections.sort(temp, comparator);
		rlt.SetResultList(temp);
	}
	
	// 顺序比较器类
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
			Collator collator = Collator.getInstance(Locale.CHINA);
			return collator.compare(i1.getValue(sortKey), i2.getValue(sortKey));

			// return i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
		}
	}

	// 逆序比较器类
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

			Collator collator = Collator.getInstance(Locale.CHINA);
			return -collator.compare(i1.getValue(sortKey), i2.getValue(sortKey));

			// return -i1.getValue(sortKey).compareTo(i2.getValue(sortKey));
		}
	}

}
