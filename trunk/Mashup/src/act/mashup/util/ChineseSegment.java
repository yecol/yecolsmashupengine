package act.mashup.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class ChineseSegment {
	//����ģʽ
	private static final ChineseSegment instance = new ChineseSegment();

	private ChineseSegment() {
	}

	//��õ���
	public static synchronized ChineseSegment getInstance() {
		return instance;
	}
	
	public ArrayList<String> getPlaces(String string){
		ArrayList<String> places=new ArrayList<String>();
		IKSegmentation ikSeg = new IKSegmentation(new StringReader(string), true);
		try {
			Lexeme l = null;
			while ((l = ikSeg.next()) != null) {
				if (l.getLexemeType() == 0){
					//System.out.println(l.getLexemeText());
				    places.add(l.getLexemeText());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return places;
	}
}
