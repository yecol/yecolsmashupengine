package act.mashup.module;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FetchRss extends AbstractListModule {
	
	//֧�ֶ�̬����

	private ArrayList<String> RssAddresses;
	private List<SyndEntry> entries;
	private DateFormat dateFormat;

	@Override
	protected void Prepare() throws Exception {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		RssAddresses = new ArrayList<String>();
		List els = en.getParas().getChildren("url", KV.gf);
		for (int i = 0; i < els.size(); i++) {
			Element e = (Element) els.get(i);
			Integer istream;
			try{
				istream= Integer.parseInt(e.getAttributeValue("istream"));
			}catch(NumberFormatException nfe){
				istream=0;
			}
			if (istream != 0)
				dynamicInputs.add(istream);
			else
				RssAddresses.add(e.getValue().trim());
		}


		if (this.en.isDynamic() == true) {
			for (Integer i : dynamicInputs) {
				RssAddresses.add(results.get(i).GetResultMap().get(i.toString()).toString());
			}
		}

		if (RssAddresses.isEmpty())
			throw new MalformedURLException();
	}

	@Override
	protected void Execute() throws Exception {
		// TODO Auto-generated method stub
		if (items.isEmpty()) {
			for (int i = 0; i < RssAddresses.size(); i++) {
				URL _url = new URL(RssAddresses.get(i));
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
					_item.setValue("publishDate", entry.getPublishedDate() == null ? dateFormat.format(timeStamp) : dateFormat.format(entry.getPublishedDate()));
					_item.setValue("description", entry.getDescription().getValue());
					items.add(_item);
				}
			}
		}
		// �����������ӳ�伯
		rlt.SetResultList(items);
	}

	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}
}