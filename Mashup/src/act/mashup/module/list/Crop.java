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

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// 获得输入节点和剪切参数
	@Override
	protected void Prepare() throws Exception {
		in = en.getInputs().get(0);
		// 获得参数

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

	// 进行裁减
	@Override
	protected void Execute() throws Exception {
		for (int i = 0; i < cropLength; i++) {
			items.add((Item) results.get(in).GetResultList().get(i));
		}
		rlt.SetResultList(items);
	}

}
