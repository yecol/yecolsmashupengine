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
 * ������
 * <ul>
 * <li>0.XMLԴ��ַ�����룩</li>
 * <li>1.ת��ΪItemԪ�ص�XPath�����룩</li>
 * <li>2+.ÿ��item�ڲ�Ԫ�ص�XPath����ѡ������</li>
 * </ul>
 * <p>
 * ���أ��ڲ����ݽṹItem���б�
 */

public class FetchXml extends AbstractModule {

	private String urlString;
	private String itemRoot;
	private ArrayList<String> itemChildren;

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// �Ӳ��������л�ò���
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

		// ��URL��ʼ����XML�ĵ�
		SAXBuilder sb = new SAXBuilder();
		URL url = null;
		Document doc = null;

		url = new URL(urlString);
		doc = sb.build(url);

		// ���XML�ĵ��ĸ��ڵ���Ϊ������
		Element rootElement = doc.getRootElement();

		// ��Ŀ�ڵ�
		List<Element> itemList = null;
		Item _item = null;

		itemList = XPath.selectNodes(rootElement, itemRoot);

		// �ֲ�ָ��
		XPath _x = null;
		String _key = null;
		for (Element e : itemList) {
			// �½�һ��item
			_item = new Item();

			for (String s : itemChildren) {
				// ��item�ڲ���ÿ����Ԫ�ز���
				_x = XPath.newInstance(s);
				// System.out.println("_key="+_key+"_value="+transformParas.get(_key)+"_xv="+_x.valueOf(e));
				_item.setValue(s, _x.valueOf(e));

			}
			items.add(_item);
		}

		// �����������ӳ�伯
		rlt.SetResultList(items);

	}

}
