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

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FetchRss extends AbstractModule {

	private String RssAddress;
	private List<SyndEntry> entries;

	@Override
	protected void Prepare() throws Exception {
		// TODO Auto-generated method stub
		RssAddress = en.getParas().getChildTextTrim("url",KV.gf);
		if (RssAddress.isEmpty() || RssAddress.length() == 0)
			throw new MalformedURLException();
	}

	@Override
	protected void Execute() throws Exception {
		// TODO Auto-generated method stub
		if (items.isEmpty()) {
			URL _url = new URL(RssAddress);
			// ��ȡRssԴ
			XmlReader _reader = new XmlReader(_url);
			SyndFeedInput _input = new SyndFeedInput();
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
				_item.setValue("publishDate", entry.getPublishedDate() == null ? timeStamp.toString() : entry.getPublishedDate().toString());
				_item.setValue("description", entry.getDescription().getValue());
				items.add(_item);
			}
		}
		// �����������ӳ�伯
		rlt.SetResultList(items);
	}
	
	public void run(EngineNode en, Map<Integer,Result>results){
		super.run(en, results);
	}
}