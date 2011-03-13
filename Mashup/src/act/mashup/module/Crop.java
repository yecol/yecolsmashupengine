package act.mashup.module;

import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Crop extends AbstractListModule {

	private Integer in;
	private Integer cropLength;


	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}


	// �������ڵ�ͼ��в���
	@Override
	protected void Prepare() throws Exception {
		in = en.getInputs().get(0);
		//��ò���
		cropLength = Integer.parseInt(en.getParas().getChildTextTrim("cropLength",KV.gf));		
	}

	
	// ���вü�
	@Override
	protected void Execute() throws Exception {
		for (int i = 0; i < cropLength; i++) {
			items.add((Item) results.get(in).GetResultList().get(i));
		}
		rlt.SetResultList(items);	
	}

}
