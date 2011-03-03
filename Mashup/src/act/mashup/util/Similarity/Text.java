package act.mashup.util.Similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import act.mashup.util.ChineseSegment;

public class Text {
	LoadDict LD = LoadDict.getDict();
	private Map<String, Word> MyWord;
	
	private int _length;

	public Text() {
	}

	public Text(String strFile) {
		MyWord = new HashMap<String, Word>();
		int _length = strFile.length();
		ChineseSegment cs = ChineseSegment.getInstance();
		ArrayList<String> tempWord = cs.getSegments(strFile);

		double num;
		double num2;
		long D = 0x5f5e100;// 100,000,000
		long Dw = 0;

		// 确保文件
		// Pattern p= Pattern.compile("[\u4e00-\u9fa5]");
		for (String str : tempWord) {
			if (!MyWord.containsKey(str)) {
				// Matcher m=p.matcher(str);
				// if (m.matches())
				// {
				// System.out.println("RegexOK");
				Word word = new Word(str);
				word.setWordFrequency(1);
				addWord(word);
				// }
			} else {
				Word word = getWordByKey(str);
				word.addWordFrequency();
			}
		}

		// System.out.println("this is MywordSet:"+MyWord.toString());

		// 
		List<String> NoNeedWord = new ArrayList<String>();
		Set<String> keySet = MyWord.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String keystr = it.next();
			if (LD.sogou.containsKey(keystr)) {
				num = ((double) (MyWord.get(keystr).getWordFrequency())) / ((double) _length);
				num2 = 0;
				Dw = Integer.parseInt(LD.sogou.get(keystr).toString());
				num2 = Math.abs(Math.log(((double) D) / ((double) Dw)));
				Word W = getWordByKey(keystr);
				W.setCharacterValue(num * num2);

			} else {
				NoNeedWord.add(keystr);
			}
		}
		// 
		for (String str : NoNeedWord) {
			deleteWord(str);
		}
	}
	public double ComputeSimilarity(Text Doc) {
		double num = 0;
		double d = 0;
		double num3 = 0;
		double num4 = 0;
		Set<String> keySet = MyWord.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String str = it.next();
			if (Doc.MyWord.containsKey(str)) {
				// a
				num += ((double) MyWord.get(str).getCharacterValue()) * ((double) Doc.MyWord.get(str).getCharacterValue()); // d1*c1
				d += ((double) MyWord.get(str).getCharacterValue()) * ((double) MyWord.get(str).getCharacterValue()); // |d1|
				num3 += ((double) Doc.MyWord.get(str).getCharacterValue()) * ((double) Doc.MyWord.get(str).getCharacterValue());// |c1|
				num4 += 1;
			}
		}

		if ((num4 / ((double) MyWord.size()) <= 0.05) || (num4 / ((double) Doc.MyWord.size()) <= 0.05)) {
			return 0;
		}

		d = Math.sqrt(d);
		num3 = Math.sqrt(num3);
		return (num / (d * num3));
	}


	public Word deleteWord(String key) {
		return MyWord.remove(key);
	}

	public void addWord(String key) {
		if (MyWord.containsKey(key)) {
			return;
		} else {
			MyWord.put(key, new Word(key));
		}
	}

	public void addWord(Word word) {
		if (MyWord.containsKey(word.getKeyString())) {
			return;
		} else {
			MyWord.put(word.getKeyString(), word);
		}
	}

	public Word getWordByKey(String key) {
		if (MyWord.containsKey(key)) {
			return MyWord.get(key);
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		Text doc1 = new Text("传摩根大通已间接投资Twitter");
		Text doc2 = new Text("摩根大通欲以4.5亿美元收购社交网站Twitter10%股权");
		Text doc3 = new Text("摩根大通拟投资Twitter 估值达40亿美元");
		Text doc4 = new Text("摩通拟4.5亿美元入股Twitter");
		Text doc5 = new Text("苹果发布新的Macbook系列产品。");
		ArrayList<Text> docList = new ArrayList<Text>();
		docList.add(doc1);
		docList.add(doc2);
		docList.add(doc3);
		docList.add(doc4);
		docList.add(doc5);
		for (int i = 0; i < 4; i++)
			for (int j = i + 1; j < 5; j++) {
				System.out.print("Compare " + (i + 1) + " " + (j + 1));
				System.out.print(" Cos=" + docList.get(i).ComputeSimilarity(docList.get(j)) + "\n");

			}

	}
}
