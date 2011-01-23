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
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <p>
 * FetchRss Module
 * </P>
 * ������0.RssԴ��ַ�����룩</br> ���أ��ڲ����ݽṹItem���б�</br>
 */

public class FetchRss {

	private Map<Integer, Result> results;
	private EngineNode en;

	private String RssAddress;
	private List<Item> items;
	private List<SyndEntry> entries;
	private Date timeStamp;
	private Result rlt;

	/**
	 * Default constructor.
	 */
	public FetchRss() {
		timeStamp = new Date();
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
			ParseRss();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			rlt.ErrorOccur("RSS��������");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("RSS������������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("��ȡRSSʧ�ܣ�");
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("RSS����");
		} finally {
			System.out.println("yecol runs here");
			System.out.println(rlt.toString());

			outputs = en.getOutputs();
			iterator = outputs.iterator();
			while (iterator.hasNext()) {
				results.put(iterator.next(), rlt);
			}
		}

	}

	// ˽�з���

	// �Ӳ��������л�õ�һ��������ΪRssԴ��ַ����
	private void Prepare() throws MalformedURLException, ParserException {
		String urlString = en.getParas().get("rssUrl");
		/*
		 * //��URL̽��RSSԴ System.out.println("begin to detect"); String line; URL
		 * myUrl = new URL("http://kczy.zjut.edu.cn/xwyy");
		 * 
		 * Parser parser = new Parser("http://kczy.zjut.edu.cn/xwyy"); NodeList
		 * list = new NodeList(); NodeFilter filter = new AndFilter(new
		 * TagNameFilter("link"), new HasAttributeFilter("type",
		 * "application/rss+xml")); for (NodeIterator e = parser.elements();
		 * e.hasMoreNodes();) { e.nextNode().collectInto(list, filter); }
		 * 
		 * System.out.println(list.elementAt(0).toPlainTextString());
		 */
		if (urlString.isEmpty() || urlString.length() == 0)
			throw new MalformedURLException();
		else
			this.RssAddress = urlString;
	}

	// ����rss
	private void ParseRss() throws IOException, IllegalArgumentException,
			FeedException {
		if (items == null) {
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
				if (!entry.getAuthor().trim().equals(""))
					_item.setValue("author", entry.getAuthor());
				_item.setValue("link", entry.getLink());
				_item.setValue("publishDate",
						entry.getPublishedDate() == null ? timeStamp.toString()
								: entry.getPublishedDate().toString());
				_item.setValue("description", entry.getDescription().getValue());
				items.add(_item);
			}

			// �����������ӳ�伯
			rlt.SetResultList(items);
		}
	}

}
