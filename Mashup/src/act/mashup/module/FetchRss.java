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
 * 参数：0.Rss源地址（必须）</br> 返回：内部数据结构Item的列表</br>
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

	// 供Engine调用的函数
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
			rlt.ErrorOccur("RSS解析错误！");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("RSS解析参数错误！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("获取RSS失败！");
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rlt.ErrorOccur("RSS错误！");
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

	// 私有方法

	// 从参数数组中获得第一个参数作为Rss源地址传入
	private void Prepare() throws MalformedURLException, ParserException {
		String urlString = en.getParas().get("rssUrl");
		/*
		 * //从URL探测RSS源 System.out.println("begin to detect"); String line; URL
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

	// 解析rss
	private void ParseRss() throws IOException, IllegalArgumentException,
			FeedException {
		if (items == null) {
			URL _url = new URL(RssAddress);
			// 读取Rss源
			XmlReader _reader = new XmlReader(_url);
			SyndFeedInput _input = new SyndFeedInput();
			items = new ArrayList<Item>();
			SyndFeed _feed = _input.build(_reader);

			// 得到Rss源中的每一个条目
			entries = _feed.getEntries();

			// 将每个条目转化为Engine的内部格式
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

			// 将结果放入结果映射集
			rlt.SetResultList(items);
		}
	}

}
