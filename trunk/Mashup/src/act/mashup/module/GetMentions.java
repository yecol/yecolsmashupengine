/**
 * 
 */
package act.mashup.module;

import java.util.List;
import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class GetMentions {

	/**
	 * 获取@当前用户的微博列表 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
        	weibo.setToken("cc3628c01b1f3a045040ec003963d9fa","0d162fd127d0deef5ab3444d0def281f");
        	List<Status> list = weibo.getMentions();
        	for(Status status : list) {
        		System.out.println( status.getId() + "  : "+status.getText());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

