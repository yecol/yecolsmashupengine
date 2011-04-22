package act.mashup.module;

import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

public class Crop extends AbstractListModule {
	
	//֧�ֶ�̬����

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
		// ��ò���

		Element e = en.getParas().getChild("cropLength", KV.gf);

		Integer istream;
		try {
			istream = Integer.parseInt(e.getAttributeValue("istream"));
		} catch (NumberFormatException nfe) {
			istream = 0;
		}
		if (en.isDynamic() == true && istream != 0)
			//results.get(i).GetResultMap().get(i.toString()).toString()
			cropLength = Integer.parseInt(results.get(istream).GetResultMap().get(istream.toString()).toString());
		else
			cropLength = Integer.parseInt(e.getValue().trim());
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