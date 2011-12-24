package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.ImageItem;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.global.VideoItem;

import com.yahoo.boss.YahooBoss;

public class EnrichMedia extends AbstractListModule {

	// ֧�ֶ�̬����

	private Integer in;
	private YahooBoss yahooBoss;
	private int relPhotoCount;// ���ͼƬ�����Ľ������ 2-3
	private int relVideoCount;// �����Ƶ�����Ľ������ 2-3

	private String searchKey;
	private ArrayList<String> segments;

	private List<ImageItem> imageItems;
	private List<VideoItem> videoItems;

	// ��Engine���õĺ���
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		Element relPhoto = en.getParas().getChild("relPhotoCount", KV.gf);
		Element relVideo = en.getParas().getChild("relVideoCount", KV.gf);

		Integer photoIstream, videoIstream;
		try {
			photoIstream = Integer.parseInt(relPhoto.getAttributeValue("istream"));
			videoIstream = Integer.parseInt(relVideo.getAttributeValue("istream"));
		} catch (NumberFormatException nfe) {
			photoIstream = 0;
			videoIstream = 0;
		}
		if (en.isDynamic() == true && photoIstream != 0) {
			relPhotoCount = Math.max(5, Integer.parseInt(results.get(photoIstream).GetResultMap().get("relPhotoCount").toString()));
			relVideoCount = Math.max(5, Integer.parseInt(results.get(videoIstream).GetResultMap().get("relVideoCount").toString()));
		} else {
			relPhotoCount = Math.max(5, Integer.parseInt(relPhoto.getValue().trim()));
			relVideoCount = Math.max(5, Integer.parseInt(relVideo.getValue().trim()));
		}

		yahooBoss = new YahooBoss();
		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
	}

	@Override
	protected void Execute() throws Exception {
		for (Iterator it = items.iterator(); it.hasNext();) {
			Item item = (Item) it.next();
			searchKey = item.getValue(KV.TITLE);
			imageItems = yahooBoss.SearchImageWithKey(searchKey, relPhotoCount);
			if(imageItems!=null){
				for(int i=0;i<imageItems.size();i++){
					item.setValue(KV., place);
				}
				
			}

			if (place == null && item.getKeys().contains(KV.DESCRIPTION)) {
				string = item.getValue(KV.DESCRIPTION);
				segments = cs.getSegments(string);
				place = ExtractGeoInformation(segments);
			}
			if (place != null && place.length() != 0) {
				item.setValue(KV.PLACE, place);
			} else if (addUngeoItems.equals("0")) {
				it.remove();
			}
		}
		rlt.SetResultList(items);

	}

	private String ExtractGeoInformation(ArrayList<String> segments) {
		for (String segment : segments) {
			if (cp.FindPlace(segment) == true)
				return segment;
		}
		return null;
	}

}
