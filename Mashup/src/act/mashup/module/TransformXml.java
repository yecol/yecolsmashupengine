package act.mashup.module;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import org.htmlparser.visitors.NodeVisitor;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.GeoRSSUtils;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

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

public class TransformXml {

	private Map<Integer, Result> results;
	private EngineNode en;
	private List<Item> items;
	private Result rlt;

	private String urlString;
	private String itemRoot;
	Map<String, String> transformParas;

	/**
	 * Default constructor.
	 */
	public TransformXml() {
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
			Transform();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("�ĵ�����ģ�ͽ�������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			rlt.ErrorOccur("IO����");
			e.printStackTrace();
		}finally{
			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while(iterator.hasNext())
			{
				results.put(iterator.next(), rlt);
			}
		}
	}

	// ˽�з���

	// �Ӳ��������л�ò���
	private void Prepare() {

		urlString = en.getParas().get("xmlUrl");
		itemRoot = en.getParas().get("itemRoot");

		// �����������������������������������������������棺�޸���en�е�ԭʼ���ݡ�����������������������������������������������������������
		en.getParas().remove("xmlUrl");
		en.getParas().remove("itemRoot");

		transformParas = en.getParas();
	}

	private void Transform() throws JDOMException, IOException {

		items = new ArrayList<Item>();

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

			for (Iterator<String> it = transformParas.keySet().iterator(); it
					.hasNext();) {

				// ��item�ڲ���ÿ����Ԫ�ز���
				_key = it.next();
				_x = XPath.newInstance(transformParas.get(_key));
				//System.out.println("_key="+_key+"_value="+transformParas.get(_key)+"_xv="+_x.valueOf(e));
				_item.setValue(_key, _x.valueOf(e));

			}
			items.add(_item);
		}

		// �����������ӳ�伯
		rlt.SetResultList(items);		
	}

}
