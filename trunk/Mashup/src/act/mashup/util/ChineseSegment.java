package act.mashup.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class ChineseSegment {
	//单例模式
	private static final ChineseSegment instance = new ChineseSegment();

	private ChineseSegment() {
	}

	//获得单例
	public static synchronized ChineseSegment getInstance() {
		return instance;
	}
	
	public ArrayList<String> getSegments(String string){
		ArrayList<String> segments=new ArrayList<String>();
		IKSegmentation ikSeg = new IKSegmentation(new StringReader(string), true);
		try {
			Lexeme l = null;
			while ((l = ikSeg.next()) != null) {
				if (l.getLexemeType() == 0){
				    segments.add(l.getLexemeText());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return segments;
	}
}
