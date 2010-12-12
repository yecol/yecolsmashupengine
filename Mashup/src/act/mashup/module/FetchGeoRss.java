package act.mashup.module;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import act.mashup.util.EngineNode;
import act.mashup.util.Item;
import act.mashup.util.Result;

import com.sun.syndication.feed.module.georss.GeoRSSModule;
import com.sun.syndication.feed.module.georss.GeoRSSUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <p>
 * FetchGeoRss Module
 * <p>
 * 参数：
 * <ul>
 * <li>0.Rss源地址（必须）</li>
 * <li>1.是否保持没有地理标记的项目（必须）</li>
 * </ul>
 * <p>
 * 返回：内部数据结构Item的列表
 */

public class FetchGeoRss {

	private Map<Integer, Result> results;
	private EngineNode en;

	private String rssAddress;
	private List<Item> items;
	private List<SyndEntry> entries;
	private Date timeStamp;
	private Result rlt;

	private final String geoUrlPrefixWithUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=true&feedUrl=";
	private final String geoUrlPrefixWithoutUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=false&feedUrl=";

	/**
	 * Default constructor.
	 */
	public FetchGeoRss() {
		timeStamp = new Date();
		rlt = new Result(Result.TYPE_LIST);
	}

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		this.en = en;
		this.results = results;
		Prepare();
		ParseRss();
	}

	// 私有方法

	// 从参数数组中获得参数传入
	private void Prepare() {
		// Rss源地址
		String urlString = en.getParas().get("rssUrl");

		// 决定如何再构造Url地址
		String addUngeoItems = en.getParas().get("addUngeoItems");
		if (addUngeoItems.equals("1")) {
			this.rssAddress = geoUrlPrefixWithUngeo + urlString;
		} else {
			this.rssAddress = geoUrlPrefixWithoutUngeo + urlString;
		}

	}

	private void ParseRss() {
		if (items == null)
			try {
				URL _url = new URL(rssAddress);

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
					GeoRSSModule geoRSSModule = GeoRSSUtils.getGeoRSS(entry);

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
					_item.setValue("lat", Double.toString(geoRSSModule
							.getPosition().getLatitude()));
					_item.setValue("Lon", Double.toString(geoRSSModule
							.getPosition().getLongitude()));
					items.add(_item);
				}
			} catch (Exception e) {
				rlt.ErrorOccur("获取GeoRSS失败");
				e.printStackTrace();
			}

		// 将结果放入结果映射集
		
		rlt.SetResultList(items);
		results.put(en.getId(), rlt);
	}

	// 打印列表
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}
}
