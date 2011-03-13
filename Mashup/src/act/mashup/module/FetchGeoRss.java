package act.mashup.module;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

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

public class FetchGeoRss extends AbstractListModule {

	private String rssAddress;
	private List<SyndEntry> entries;

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	// ��ӡ�б�
	private void PrintItems() {
		for (Item i : items) {
			System.out.println(i.toString());
		}
	}

	@Override
	protected void Prepare() throws Exception {
		String urlString = en.getParas().getChildTextTrim("url", KV.gf);

		if (urlString.isEmpty() || urlString.length() == 0)
			throw new MalformedURLException();

		// ��������ٹ���Url��ַ
		String addUngeoItems = en.getParas().getChildTextTrim("addUngeoItems", KV.gf);
		if (addUngeoItems.equals("1")) {
			this.rssAddress = KV.geoUrlPrefixWithUngeo + urlString;
		} else {
			this.rssAddress = KV.geoUrlPrefixWithoutUngeo + urlString;
		}

	}

	@Override
	protected void Execute() throws Exception {
		if (items.isEmpty()) {
			URL _url = new URL(rssAddress);

			// ��ȡRssԴ
			XmlReader _reader = new XmlReader(_url);
			SyndFeedInput _input = new SyndFeedInput();
			SyndFeed _feed = _input.build(_reader);

			// �õ�RssԴ�е�ÿһ����Ŀ
			entries = _feed.getEntries();

			// ��ÿ����Ŀת��ΪEngine���ڲ���ʽ
			Item _item;
			for (SyndEntry entry : entries) {
				GeoRSSModule geoRSSModule = GeoRSSUtils.getGeoRSS(entry);

				_item = new Item();
				_item.setValue("title", entry.getTitle());
				if (!entry.getAuthor().trim().equals(""))
					_item.setValue("author", entry.getAuthor());
				_item.setValue("link", entry.getLink());
				_item.setValue("publishDate", entry.getPublishedDate() == null ? timeStamp.toString() : entry.getPublishedDate().toString());
				_item.setValue("description", entry.getDescription().getValue());
				_item.setValue("lat", Double.toString(geoRSSModule.getPosition().getLatitude()));
				_item.setValue("Lon", Double.toString(geoRSSModule.getPosition().getLongitude()));
				items.add(_item);
			}
		}
		// �����������ӳ�伯
		rlt.SetResultList(items);

	}
}
