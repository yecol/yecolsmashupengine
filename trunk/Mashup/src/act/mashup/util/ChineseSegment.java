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
	public ArrayList<String> getSegments(StringBuffer stringBuffer){
		return getSegments(stringBuffer.toString());
	}
	
	public void testSegment(){
		String s="��Ȫ�а˶���ƽ����";
		//System.out.println(getSegments(s).toString());
	}
	
	public static void main(String[] args){
		ChineseSegment cs=ChineseSegment.getInstance();
		cs.testSegment();
	}
}
