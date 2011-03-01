package act.mashup.module;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

/**
 * <p>
 * Transform XML Module
 * <p>
 * 参数：
 * <ul>
 * <li>0.XML源地址（必须）</li>
 * <li>1.转化为Item元素的XPath（必须）</li>
 * <li>2+.每个item内部元素的XPath（可选个数）</li>
 * </ul>
 * <p>
 * 返回：内部数据结构Item的列表
 */

public class FetchXml extends AbstractModule {

	private String urlString;
	private String itemRoot;
	private ArrayList<String> itemChildren;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// 从参数数组中获得参数
	@Override
	protected void Prepare() throws Exception {
		
		itemChildren=new ArrayList<String>();
		urlString = en.getParas().getChildTextTrim("url", KV.gf);
		itemRoot = en.getParas().getChildTextTrim("itemRoot", KV.gf);

		List iChildren = en.getParas().getChild("itemChildren", KV.gf).getChildren("itemChild", KV.gf);
		for (Object o : iChildren) {
			Element el = (Element) o;
			System.out.println("text:"+el.getText()+" value:"+el.getValue());
			itemChildren.add(el.getValue());
		}

	}

	@Override
	protected void Execute() throws Exception {

		// 从URL开始解析XML文档
		SAXBuilder sb = new SAXBuilder();
		URL url = null;
		Document doc = null;

		url = new URL(urlString);
		doc = sb.build(url);

		// 获得XML文档的根节点作为上下文
		Element rootElement = doc.getRootElement();

		// 条目节点
		List<Element> itemList = null;
		Item _item = null;

		itemList = XPath.selectNodes(rootElement, itemRoot);

		// 局部指针
		XPath _x = null;
		String _key = null;
		for (Element e : itemList) {
			// 新建一个item
			_item = new Item();

			for (String s : itemChildren) {
				// 将item内部的每个子元素补齐
				_x = XPath.newInstance(s);
				// System.out.println("_key="+_key+"_value="+transformParas.get(_key)+"_xv="+_x.valueOf(e));
				_item.setValue(s, _x.valueOf(e));

			}
			items.add(_item);
		}

		// 将结果放入结果映射集
		rlt.SetResultList(items);

	}

}
