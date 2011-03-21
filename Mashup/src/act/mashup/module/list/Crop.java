package act.mashup.module.list;

import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.module.AbstractListModule;

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
		// ��ò���

		// cropLength =
		// Integer.parseInt(en.getParas().getChildTextTrim("cropLength",KV.gf));
		Element e = en.getParas().getChild("cropLength", KV.gf);

		Integer istream;
		try {
			istream = Integer.parseInt(e.getAttributeValue("istream"));
		} catch (NumberFormatException nfe) {
			istream = 0;
		}
		if (en.isDynamic() == true && istream != 0)
			cropLength = Integer.parseInt(results.get(istream).GetResultMap().get("cropLength").toString());
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
