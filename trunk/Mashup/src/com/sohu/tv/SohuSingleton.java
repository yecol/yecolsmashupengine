package com.sohu.tv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import act.mashup.global.VideoItem;

public class SohuSingleton {
	private static String apiKey = "3249826a4be8785656c51e5e071b2d08";
	private static String secretKey = "GnAhbxhFtGDOkw3q3arYxbG4jPbPV8cs";
	private static String oauthToken = "";
	private static String tokenSecret = "";

	/**
	 * @param args
	 * @throws SoException
	 * @throws JSONException
	 */
	public static List<VideoItem> searchVideoItemByKey(String searchKey, int resultSize) {
		SoClient client = new SoClient(apiKey, secretKey, oauthToken, tokenSecret);
		HashMap<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("key", searchKey);
		SetInfo resultSets = client.search(searchParam);

		List<VideoItem> videoItems = new ArrayList<VideoItem>();

		if (resultSets == null) {
			return null;
		}

		for (int i = 0; i < Math.min(resultSize, resultSets.getCount()); i++) {

			VideoItem videoItem = new VideoItem();
			videoItem.setVideoTitle(resultSets.getVideos().get(i).getTv_name());
			videoItem.setVideoDesc(resultSets.getVideos().get(i).getTv_desc());
			videoItem.setVideoDuration(resultSets.getVideos().get(i).getTip());
			videoItem.setVideoThumbURL(resultSets.getVideos().get(i).getVer_small_pic());
			videoItem.setVideoURL(resultSets.getVideos().get(i).getTv_url());

			videoItems.add(videoItem);
		}

		if (videoItems.size() == 0) {
			return null;
		} else
			System.out.println(videoItems.toString());
		return videoItems;

	}

	public static void main(String[] args) throws JSONException, SoException {
		searchVideoItemByKey("������", 3);
	}

	// teleplay/category ���Ӿ�ķ��ർ��
	// movie/category ��Ӱ�ķ��ർ��
	// zongyi/category ����Ƭ�ķ��ർ��
	public static void getCategory(SoClient client) throws JSONException, SoException {
		List<Category> list = client.getCategory("movie/category");
	}

	/**
	 * ȡ�ý�����Ƶ��Ϣ
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	// news/focus ��ȡ��������
	// teleplay/focus ��ȡ���յ��Ӿ���Ľ����Ŀ
	// movie/focus ��ȡ���ս����Ӱ
	public static void getFocus(SoClient client) throws JSONException, SoException {
		List<FocusData> fds = client.getFocus("teleplay/focus");
	}

	/**
	 * ȡ���Ƽ���Ƶ��Ϣ
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	// news/recommend ��ȡ�Ƽ�����
	// teleplay/recommend ��ȡ���յ��Ӿ�����Ƽ���Ŀ
	// movie/recommend ��ȡ���յ�Ӱ����Ƽ���Ŀ
	// zongyi/recommend ��ȡ���շ����Ƽ�
	public static void getRecommend(SoClient client) throws JSONException, SoException {
		List<Video> list = client.getRecommend("teleplay/recommend");
	}

	/**
	 * ȡ��ר����Ϣ
	 * 
	 * @param client
	 * @throws JSONException
	 * @throws SoException
	 */
	public static void getSetInfo(SoClient client) throws JSONException, SoException {
		Video v = client.getSetInfo(310153);
	}

	/**
	 * ȡ�÷ּ��б�
	 * 
	 * @param client
	 * @throws JSONException
	 * @throws SoException
	 */
	public static void getSetList(SoClient client) throws JSONException, SoException {
		SetInfo si = client.getSetList(1003377, 1, 20);
	}

	/*
	 * ��Ӱ���а� movie/top/views/daily ��Ӱ���а� �� �����հ� movie/top/views/weekly ��Ӱ���а� ��
	 * �����ܰ� movie/top/views/monthly ��Ӱ���а� �� �����°� movie/top/views/all ��Ӱ���а� ��
	 * ����ȫ���� movie/top/score/perfect ��Ӱ���ְ�: ��Ƭ�� movie/top/score/good ��Ӱ���ְ�: ��Ƭ��
	 * movie/top/score/common ��Ӱ���ְ�: һ��� movie/top/score/poor ���ְ�: ��Ƭ��
	 * ******************************* ���Ӿ����а� teleplay/top/views/daily ���Ӿ����а� ��
	 * �����հ� teleplay/top/views/weekly ���Ӿ����а� �� �����ܰ� teleplay/top/views/monthly
	 * ���Ӿ����а� �� �����°� teleplay/top/views/all ���Ӿ����а� �� ����ȫ����
	 * teleplay/top/score/perfect ���Ӿ����ְ�: ��Ƭ�� teleplay/top/score/good ���Ӿ����ְ�:
	 * ��Ƭ�� teleplay/top/score/common ���Ӿ����ְ�: һ��� teleplay/top/score/poor ���Ӿ����ְ�:
	 * ��Ƭ�� ************************************** ����Ƭ���а� zongyi/top/views/daily
	 * ����Ƭ���а� �� �����հ� zongyi/top/views/weekly ����Ƭ���а� �� �����ܰ�
	 * zongyi/top/views/monthly ����Ƭ���а� �� �����°� zongyi/top/views/all ����Ƭ���а� ��
	 * ����ȫ���� zongyi/top/score/perfect ����Ƭ���ְ�: ��Ƭ�� zongyi/top/score/good ����Ƭ���ְ�:
	 * ��Ƭ�� zongyi/top/score/common ����Ƭ���ְ�: һ��� zongyi/top/score/poor ����Ƭ���ְ�:
	 * ��Ƭ���
	 */
	public static void getTop(SoClient client) throws JSONException, SoException {
		List<Video> videos = client.getTop("movie/top/views/daily");

	}

	/**
	 * ȡ����Ƶ�ֶ���Ϣ
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	public static void getVideo(SoClient client) throws JSONException, SoException {
		VideoSplits vs = client.getVideo(310153);
		System.out.println("TV_NAME : " + vs.getTvName());
	}

	public static void test(SoClient client) throws JSONException, SoException {

		Video vi = client.getSetInfo(1003377);
		System.out.println(vi.getTv_name());
	}

}
