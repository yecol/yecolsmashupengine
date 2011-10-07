/**
 *
 */
package act.mashup.module;

import java.util.List;

import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetPublicTimeline {

	/**
	 * 获取最新更新的公共微博消息
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	try {
			//获取前20条最新更新的公共微博消息
    		/*Got request token.
    		Request token: e2bc5879a97f18adb152da61ac48c45d
    		Request token secret: a02dd442bc9855575aade322880ea8f1*/
    		Weibo weibo = new Weibo();
			//weibo.setToken("cc3628c01b1f3a045040ec003963d9fa","0d162fd127d0deef5ab3444d0def281f");
			 List<Status> statuses =weibo.getPublicTimeline();
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +
	                               status.getText() + ":" +
	                               status.getCreatedAt());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
