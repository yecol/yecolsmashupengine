
package act.mashup.module;

import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

/**
 * @author sina
 *
 */
public class GetUserTimeline {

	/**
	 * ��ȡ�û�������΢����Ϣ�б� 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			args=new String[]{"cc3628c01b1f3a045040ec003963d9fa","0d162fd127d0deef5ab3444d0def281f","1922746481"};
			weibo.setToken(args[0],args[1]);
			Paging pag = new Paging();
			pag.setSinceId(3343021761165196l);
			pag.setCount(200);
			//��ȡ24Сʱ��ǰ20���û���΢����Ϣ;args[2]:�û�ID
			List<Status> statuses = weibo.getUserTimeline(args[2],pag);
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +status.getId()+":"+
	                               status.getText() + status.getOriginal_pic());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
