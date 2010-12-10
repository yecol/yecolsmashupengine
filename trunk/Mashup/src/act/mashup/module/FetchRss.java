package act.mashup.module;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
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

import act.mashup.util.EngineNode;
import act.mashup.util.Item;

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
 * FetchRss Module
 * </P>
 * ������0.RssԴ��ַ�����룩</br> ���أ��ڲ����ݽṹItem���б�</br>
 */

public class FetchRss {

	private Map<Integer, List> results;
	private EngineNode en;

	private String RssAddress;
	private List<Item> items;
	private List<SyndEntry> entries;
	private Date timeStamp;

	/**
	 * Default constructor.
	 */
	public FetchRss() {
		timeStamp = new Date();
	}

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, List> results) {
		this.en = en;
		this.results = results;
		try {
			Prepare();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseRss();
	}

	// ˽�з���

	// �Ӳ��������л�õ�һ��������ΪRssԴ��ַ����
	private void Prepare() throws IOException, ParserException {
		String urlString = en.getParas().get("rssUrl");

		System.out.println("begin to detect");
		String line;
		URL myUrl = new URL("http://kczy.zjut.edu.cn/xwyy");

		Parser parser = new Parser("http://kczy.zjut.edu.cn/xwyy");
		NodeList list = new NodeList();
		NodeFilter filter = new AndFilter(new TagNameFilter("link"),
				new HasAttributeFilter("type", "application/rss+xml"));
		for (NodeIterator e = parser.elements(); e.hasMoreNodes();) {
			e.nextNode().collectInto(list, filter);
		}

		System.out.println(list.elementAt(0).toPlainTextString());

		this.RssAddress = urlString;
	}

	private void ParseRss() {
		if (items == null)
			try {
				URL _url = new URL(RssAddress);

				// ��ȡRssԴ
				XmlReader _reader = new XmlReader(_url);
				SyndFeedInput _input = new SyndFeedInput();
				items = new ArrayList<Item>();
				SyndFeed _feed = _input.build(_reader);

				// �õ�RssԴ�е�ÿһ����Ŀ
				entries = _feed.getEntries();

				// ��ÿ����Ŀת��ΪEngine���ڲ���ʽ
				Item _item;
				for (SyndEntry entry : entries) {
					_item = new Item();
					_item.setValue("title", entry.getTitle());
					_item.setValue("author", entry.getAuthor());
					_item.setValue("link", entry.getLink());
					_item.setValue(
							"publishDate",
							entry.getPublishedDate() == null ? timeStamp
									.toString() : entry.getPublishedDate()
									.toString());
					_item.setValue("description", entry.getDescription()
							.getValue());
					items.add(_item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		// �����������ӳ�伯
		results.put(en.getId(), items);
	}

	// ��ӡ�б�
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

}
