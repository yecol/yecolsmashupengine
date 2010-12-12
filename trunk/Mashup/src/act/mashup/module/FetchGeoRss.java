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
 * ������
 * <ul>
 * <li>0.RssԴ��ַ�����룩</li>
 * <li>1.�Ƿ񱣳�û�е����ǵ���Ŀ�����룩</li>
 * </ul>
 * <p>
 * ���أ��ڲ����ݽṹItem���б�
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

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		this.en = en;
		this.results = results;
		Prepare();
		ParseRss();
	}

	// ˽�з���

	// �Ӳ��������л�ò�������
	private void Prepare() {
		// RssԴ��ַ
		String urlString = en.getParas().get("rssUrl");

		// ��������ٹ���Url��ַ
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
				rlt.ErrorOccur("��ȡGeoRSSʧ��");
				e.printStackTrace();
			}

		// �����������ӳ�伯
		
		rlt.SetResultList(items);
		results.put(en.getId(), rlt);
	}

	// ��ӡ�б�
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}
}
