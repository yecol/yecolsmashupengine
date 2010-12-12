package act.mashup.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

/**
 * <p>
 * Merge Module
 * </P>
 * ��������</br> ���أ��ڲ����ݽṹItem���б�</br>
 */
public class Merge {
	private Map<Integer, Result> results;
	private EngineNode en;
	private ArrayList<Integer> ins;
	private ArrayList<Item> items;
	private Date timeStamp;

	/**
	 * Default constructor.
	 */
	public Merge() {
		timeStamp = new Date();
		items=new ArrayList<Item>();
	}

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		this.en = en;
		this.results = results;
		Prepare();
		DoMerge();
	}

	// ˽�з���

	//�����������ڵ�
	private void Prepare() {
		ins = en.getInputs();
	}

	//���кϲ�
	//�����������������������������������������������棺ָ��ϲ�������������������������������������������������������������
	private void DoMerge() {
		for(Integer i:ins){
			items.addAll(results.get(i).GetResultList());
		}
		Result rlt = new Result(Result.TYPE_LIST);
		rlt.SetResultList(items);
		results.put(en.getId(), rlt);
	}

	// ��ӡ�б�
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
