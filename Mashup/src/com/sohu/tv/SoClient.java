package com.sohu.tv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.sohu.tv.util.URLEncodeUtils;

/**
 * @author chengxinsun
 * 
 */
public class SoClient {

	private static final Log log = LogFactory.getLog(SoClient.class);

	public static String API_URL = "http://api.tv.sohu.com/";

	public static String PLAY_URL = "http://open.tv.sohu.com/";

	public static String FORMAT = ".json";

	public static String CONSUMER_KEY = "";
	public static String CONSUMER_SECRET = "";

	private static OAuthProvider provider = null;
	private OAuthConsumer consumer;
	static {
		if (null == provider)
			provider = new DefaultOAuthProvider(
					"http://api.tv.sohu.com/oauth/request_token",
					"http://api.tv.sohu.com/oauth/access_token",
					"http://api.tv.sohu.com/oauth/authorize");
	}

	public SoClient(String consumer_key, String consumer_secret) {
		consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
		CONSUMER_KEY = consumer_key;
		CONSUMER_SECRET = consumer_secret;
	}

	public SoClient(String consumer_key, String consumer_secret, String token,
			String token_secret) {
		consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
		consumer.setTokenWithSecret(token, token_secret);
		CONSUMER_KEY = consumer_key;
		CONSUMER_SECRET = consumer_secret;
	}

	private String getReponseByUrl(String url) throws SoException {
		try {
			URL myUrl = new URL(url);
			HttpURLConnection request = (HttpURLConnection) myUrl
					.openConnection();
			request.setDoOutput(true);
			request.setRequestMethod("GET");
			// ���������ǩ��
			consumer.sign(request);

			System.out
					.println("Sending request..." + consumer.getConsumerKey());
			request.connect();
			System.out.println("Response: " + request.getResponseCode() + " "
					+ request.getResponseMessage());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			StringBuffer result = new StringBuffer();
			String b = null;
			while ((b = reader.readLine()) != null) {
				result.append(b);
			}
			return result.toString();
		} catch (Exception ex) {
			throw new SoException(ex);
		}
	}

	/**
	 * �����Ƶ��Ϣ
	 * 
	 * @param vid
	 *            ��Ƶid
	 * @return Video ��Ƶ����
	 **/
	public VideoSplits getVideo(long vid) {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + "video/" + vid
					+ FORMAT, map));
			JSONObject json = new JSONObject(response);
			return VideoSplits.constructVideoSplitss(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * ���ĳ��ר������Ϣ
	 * 
	 * @param vid
	 * @return
	 */
	public Video getSetInfoByVid(long vid) {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + "set/info/v/"
					+ vid + FORMAT, map));
			JSONObject json = new JSONObject(response);
			return Video.getSetInfoByJSON(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * ���ĳ��ר������Ϣ
	 * 
	 * @param vid
	 * @return
	 */
	public Video getSetInfo(long vid) throws JSONException, SoException {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + "set/info/"
					+ vid + FORMAT, map));
			JSONObject json = new JSONObject(response);
			return Video.getSetInfoByJSON(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * ���ĳ��ר������Ƶ�б���Ϣ
	 * 
	 * @param setId
	 *            ��Ƶר��id
	 * @param pid
	 *            ��ҳ��id
	 * @return Video List ��Ƶ�����б�
	 **/
	public SetInfo getSetList(long sid, int page, int size) {
		try {
			HashMap hs = new HashMap();
			hs.put("api_key", CONSUMER_KEY);
			hs.put("page", page);
			hs.put("pageSize", size);
			String response = getReponseByUrl(getURL(API_URL + "set/list/"
					+ sid + FORMAT, hs));
			JSONObject json = new JSONObject(response);
			return SetInfo.constructVideos(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * teleplay/top/views/daily ���Ӿ����а� �� �����հ� teleplay/top/views/weekly ���Ӿ����а� ��
	 * �����ܰ� teleplay/top/views/all ���Ӿ����а� �� ����ȫ���� teleplay/top/score/perfect
	 * ���Ӿ����ְ�: ��Ƭ�� teleplay/top/score/good ���Ӿ����ְ�: ��Ƭ�� teleplay/top/score/common
	 * ���Ӿ����ְ�: һ��� teleplay/top/score/poor ���Ӿ����ְ�: ��Ƭ��
	 * 
	 */
	public List<Video> getTop(String uri) {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + uri + FORMAT,
					map));
			JSONObject json = new JSONObject(response);
			return Video.constructVideos(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ArrayList<Video>();
	}

	/**
	 * teleplay/category ���Ӿ�ķ��ർ�� movie/category ��Ӱ�ķ��ർ�� zongyi/category
	 * ����Ƭ�ķ��ർ��
	 * 
	 **/
	public List<Category> getCategory(String uri) {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + uri + FORMAT,
					map));
			System.out.println(response);
			JSONObject json = new JSONObject(response);
			return Category.constructCategorys(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ArrayList<Category>();
	}

	/**
	 * ���ݹؼ�������
	 * 
	 **/
	public SetInfo search(HashMap<String, Object> map) {
		try {
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(API_URL + "search"
					+ FORMAT, map));
			JSONObject json = new JSONObject(response);
			System.out.println("json : " + json);
			return SetInfo.constructVideos(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * news/focus ��ȡ�������� teleplay/focus ��ȡ���յ��Ӿ���Ľ����Ŀ movie/focus ��ȡ���յ�Ӱ��Ľ����Ŀ
	 * zongyi/focus ��ȡ����������Ľ����Ŀ
	 * 
	 **/
	public List<FocusData> getFocus(String uri){
		try{
		HashMap map = new HashMap();
		map.put("api_key", CONSUMER_KEY);
		String response = getReponseByUrl(getURL(API_URL + uri + FORMAT, map));
		JSONObject json = new JSONObject(response);
		return FocusData.constructFocusDatas(json);
		}catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ArrayList<FocusData>();
	}

	/**
	 * news/recommend ��ȡ�Ƽ����� teleplay/recommend ��ȡ���յ��Ӿ�����Ƽ���Ŀ movie/recommend
	 * ��ȡ���յ�Ӱ����Ƽ���Ŀ zongyi/recommend ��ȡ������������Ƽ���Ŀ
	 * 
	 **/
	public List<Video> getRecommend(String apiurl) {
		try {
			HashMap map = new HashMap();
			map.put("api_key", CONSUMER_KEY);
			String response = getReponseByUrl(getURL(apiurl + FORMAT, map));
			JSONObject json = new JSONObject(response);
			return Video.constructVideos(json);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return new ArrayList<Video>();
	}

	/**
	 * ƴ�Ӳ���
	 * 
	 * @param map
	 * @return
	 */
	protected String getParameters(HashMap<String, Object> map) {

		StringBuffer buf = new StringBuffer();
		if (null == map) {
			return "";
		}
		for (String key : map.keySet()) {
			System.out.println("key : " + key);
			System.out.println(map.get(key));

			buf.append("&");
			try {
				String value = map.get(key).toString();

				buf.append(URLEncoder.encode(key, "UTF-8"))
						.append("=")
						.append(URLEncodeUtils.isURLEncoded(value) ? value
								: URLEncodeUtils.encodeURL(value));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
		}
		log.info("URL : {" + buf.toString() + "}");
		return buf.toString();
	}

	/**
	 * ������������api_key
	 * 
	 * @param uri
	 * @param map
	 * @return
	 */
	protected String getURL(String uri, HashMap<String, Object> map) {
		if (uri.indexOf("?") == -1) {
			uri += "?";
		} else {
			uri += "&";
		}
		StringBuffer buf = new StringBuffer();
		buf.append(uri).append(getParameters(map));
		System.out.println(buf.toString());
		return buf.toString();
	}
}
