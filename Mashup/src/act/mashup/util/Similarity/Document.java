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

public class Document {
	LoadDict LD = LoadDict.getDict();
	private Map<String, Word> MyWord;

	public Document() {
	}

	public Document(String strFile) {
		MyWord = new HashMap<String, Word>();
		int _length = strFile.length();
		ChineseSegment cs = ChineseSegment.getInstance();
		ArrayList<String> tempWord = cs.getSegments(strFile);

		double num;
		double num2;
		long D = 0x5f5e100;// 100,000,000
		long Dw = 0;

		// �����Ƶ
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

		// ����ʵ�����ֵ
		List<String> NoNeedWord = new ArrayList<String>();
		Set<String> keySet = MyWord.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String keystr = it.next();
			if (LD.sogou.containsKey(keystr)) {
				num = ((double) (MyWord.get(keystr).getWordFrequency()))
						/ ((double) _length);
				num2 = 0;
				Dw = Integer.parseInt(LD.sogou.get(keystr).toString());
				num2 = Math.abs(Math.log(((double) D) / ((double) Dw)));
				Word W = getWordByKey(keystr);
				W.setCharacterValue(num * num2);

			} else {
				NoNeedWord.add(keystr);
			}
		}
		// ȥ�����ô�
		for (String str : NoNeedWord) {
			deleteWord(str);
		}
	}

	public double SimilitudeValueToDocumentUsingCos(Document Doc) {
		double num = 0;
		double d = 0;
		double num3 = 0;
		double num4 = 0;
		Set<String> keySet = MyWord.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String str = it.next();
			if (Doc.MyWord.containsKey(str)) {
				// a
				num += ((double) MyWord.get(str).getCharacterValue())
						* ((double) Doc.MyWord.get(str).getCharacterValue()); // d1*c1
				d += ((double) MyWord.get(str).getCharacterValue())
						* ((double) MyWord.get(str).getCharacterValue()); // |d1|
				num3 += ((double) Doc.MyWord.get(str).getCharacterValue())
						* ((double) Doc.MyWord.get(str).getCharacterValue());// |c1|
				num4 += 1;
			}
		}

		if ((num4 / ((double) MyWord.size()) <= 0.05)
				|| (num4 / ((double) Doc.MyWord.size()) <= 0.05)) {
			return 0;
		}

		d = Math.sqrt(d);
		num3 = Math.sqrt(num3);
		return (num / (d * num3));
	}


	private int _length;

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
		Document doc1 = new Document(
				"  �Լ��ι�����������ô������أ�����ͬ�İɡ��˳�;����İ�����Ŀɲ�С�������������ں���ޱ������Է�������С���ɴ󲡡����Դˣ�������ר�Ҹ��������½��飺����������ࡡ����;�г������ܻ�մȾ���ҳ������͡���ʬ�ȣ��粻��ʱ��ϴ����Щ�����Ӱ�쳵�����ۣ�������ʴ���ᡣ���˳��������ڵ����Ҳ���ݺ��ӣ������Ƕ���͵�̺�����׻���ҳ������˿��Ų������ʱ��һ�������̳�ù�ߡ�ϸ�������ϴ��ͬʱӦ������������ϴ����������మ�����������̷������������������۽��������ϣ�����㲻��ͨ������ϴ�����������ͨϴ��̯ϴ���������۵���������׻���ʴ���̡���������е����й��β䣬Ӧ����ȥרҵ���飬������ܵ����͵׿����Ѷ�©�͡��������������˻���ʴ����ø���������װ�ס�������̥�����̥������;��ʻ������ĥ���̥ѹ���㣬������Ӧ������̥��⣬�鿴̥ѹ�Ƿ����������������˺ۣ�ĥ����Ȼ�������˵���̥Ӧ�������������򽫵��±�̥�����⾭����ʱ�������̥��λ��ʧЧ�����ֳ�����ƫ�����������ֶ�λ�Ͷ�ƽ��ɰ�����У��̥��λ����Һ��顡��������;��ʻ�����͡���ȴҺ���ķǳ��󣬹�����Ӧ��ʱ�鿴����������Ӧ���ϲ��䡣����͡���ȴҺ���ַ��ڡ����ʾ�������������������ǰ�賹��������䡢ˮ�䣬��ֹ��Һ��Ⱦ�����⣬����ҳ������ڶ࣬�����˻�������Ӧ��ʱ����������������衣���������Ρ������ٻ������ٰ조���졮��һ�����������¡����2006��5��13��ǰ�������������Ρ������ٻ���˽�����������������̥��ȫ��⡢��̥�䵪�����յ���ڼ����ϴ�ȷ��񡣻�ڼ䣬���������Ρ������ٻ����ܡ��ⴥý�����񣬻�����ֵ400Ԫ�ġ�������ϴ����������װ�ס���Ʒ��������һ����ֵ�Żݣ����ܡ�PPS��Ӿ��Ĥ�����񣬻�����ֵ180Ԫ�ġ�Ӧ�������䡱��");
		Document doc2 = new Document(
				"��������֮������������Ⱦ�����ص�ʱ�̡���೵���ڼݳ�ʱͨ�����ŵ�һ�ɡ���ζ��������������Ҳ�����������Ŷ��ѣ����������ܡ��ⲻ֪��������ζ���ǳ��ڿ�����Ⱦ�ı�־���й����ڿ���������ĵĵ�����������ڿ�����Ⱦ����Ҫ�Ǽ�ȩ�ͱ����ꡣ��ȩ������������ɳ���桢����װ�ε�װ�β��ϣ�������Ҫ���Խ�ճ�����������������ռ���С�����ڿ����������ޣ����������ܷ��Ժã���˳��ڵ��к����峬���������ɵ�Σ���Ծ��Եø��󡣡������ڿ�����Ⱦ�������³��ڿ���ζ�����ţ������ص��ǣ���Щ�к����ʿ��ܻ����ϵͳ������ϵͳ���ڷ���ϵͳ�Լ���ֳϵͳ��������Ӱ�죬�����п����°���һ������������װ��Խ�࣬�������ڿ�����Ⱦ�Ŀ���Խ���ر������ļ������ڿ��յ����������գ����³��ڿ������ܼ�ʱ��ͨ����һ���Ӿ��˳��ڿ�������Ⱦ����������Ⱦ�Ļ����мݳ����ᵼ�¼�ʻԱͷ�Ρ����롢���ԵȲ�����Ӧ��������������ѹ�ַ��ꡢע�����޷����У����½�ͨ�¹ʡ�����������ʾ���¹���ս��й�����װ�޵ĳ�������ʹ��ǰ��Ӧ�Ƚ���������������������е��Լ��ĳ�������ζ������Ҳ������Ϊ���Լ������彡�������ǽ���һ�γ����������ڽ��г�������ʱ��Ӧ����ѡ��רҵ�Ĳ�Ʒ���豸�����������¼�����ˮֻ����ѹ����ζ�����ã��������������ڵı�����ȩ���к����ʡ�");
		Document doc3 = new Document(
				"  ��Ҫ��ô�ж�����ͬһ�������ء��Ѱ����е�ʱ�򽲵���ͬһ���¡��Լ��ι�ȥ�����⾭���˳�;����İ�����Ŀɲ�С�������������ں���ޱ������Է�������С���ɴ󲡡����Դˣ�������ר�Ҹ��������½��飺����������ࡡ����;�г������ܻ�մȾ���ҳ������͡���ʬ�ȣ��粻��ʱ��ϴ����Щ�����Ӱ�쳵�����ۣ�������ʴ���ᡣ���˳��������ڵ����Ҳ���ݺ��ӣ������Ƕ���͵�̺�����׻���ҳ������˿��Ų������ʱ��һ�������̳�ù�ߡ�ϸ�������ϴ��ͬʱӦ������������ϴ����������మ�����������̷������������������۽��������ϣ�����㲻��ͨ������ϴ�����������ͨϴ��̯ϴ���������۵���������׻���ʴ���̡���������е����й��β䣬Ӧ����ȥרҵ���飬������ܵ����͵׿����Ѷ�©�͡��������������˻���ʴ����ø���������װ�ס�������̥�����̥������;��ʻ������ĥ���̥ѹ���㣬������Ӧ������̥��⣬�鿴̥ѹ�Ƿ����������������˺ۣ�ĥ����Ȼ�������˵���̥Ӧ�������������򽫵��±�̥�����⾭����ʱ�������̥��λ��ʧЧ�����ֳ�����ƫ�����������ֶ�λ�Ͷ�ƽ��ɰ�����У��̥��λ����Һ��顡��������;��ʻ�����͡���ȴҺ���ķǳ��󣬹�����Ӧ��ʱ�鿴����������Ӧ���ϲ��䡣����͡���ȴҺ���ַ��ڡ����ʾ�������������������ǰ�賹��������䡢ˮ�䣬��ֹ��Һ��Ⱦ�����⣬����ҳ������ڶ࣬�����˻�������Ӧ��ʱ����������������衣���������Ρ������ٻ������ٰ조���졮��һ�����������¡����2006��5��13��ǰ�������������Ρ������ٻ���˽�����������������̥��ȫ��⡢��̥�䵪�����յ���ڼ����ϴ�ȷ��񡣻�ڼ䣬���������Ρ������ٻ����ܡ��ⴥý�����񣬻�����ֵ400Ԫ�ġ�������ϴ����������װ�ס���Ʒ��������һ����ֵ�Żݣ����ܡ�PPS��Ӿ��Ĥ�����񣬻�����ֵ180Ԫ�ġ�Ӧ�������䡱��");

		Document doc4 = new Document(
				"������2��25�յ� �ݻ������ձ�������������ƻ����˾���շ������¿�MacBook Pro�ʼǱ����ԣ��¿�MacBook Pro���Խ�����Ӣ�ض���һ������������������ݴ��似����ƻ����˾��ʾ�����°�MacBook Pro�ʼǱ����Խ����г����׿���ø������ݴ��似���Ĳ�Ʒ���ü�����ΪThunderbolt���ù�˾��ʾ��Ԥ��Thunderbolt��������Ϊ�µĸ������ݴ����׼���õ��㷺Ӧ�á�ƻ����˾����ʾ�����°�MacBook Pro����ʹ��Ӣ�ض����µ�˫�˺��ĺ�Intel Core���������������ٶȽ��ﵽԭ����������");
		Document doc5 = new Document(
				"����ʱ��2��25�������Ϣ���ݹ���ý�屨����ƻ���¿�MacBook Pro�ʼǱ��ĵ����������Ϊ7Сʱ��������ǰ15Ӣ���17Ӣ����8��9Сʱ��13Ӣ����10Сʱ�������ƣ�ƻ���Ѿ���ʼʹ��һ���µĵ���������Է�����ͨ��������Ϊ����������Э����ԡ��ķ������в��Եĵ���������ӷ�����ʵʹ����������ֲ��Է����ľ�������ǣ���ÿ���������ֻ�����ʾ���ȶ�Ϊ50%��Ȼ���������25�������е���վ������ʹ����Щ��վ����Ҫ���ܣ����а�������Flash��Ƶ�ȡ�ƻ��ָ���������ֻ����ú�ʹ��ģʽ�Ĳ��죬���Խ������Ҳ��������ͬ��");

		ArrayList<Document> docList = new ArrayList<Document>();
		docList.add(doc1);
		docList.add(doc2);
		docList.add(doc3);
		docList.add(doc4);
		docList.add(doc5);
		for (int i = 0; i < 4; i++)
			for (int j = i + 1; j < 5; j++) {
				System.out.print("Compare " + (i + 1) + " " + (j + 1));
				System.out.print(" Cos="
						+ docList.get(i).SimilitudeValueToDocumentUsingCos(
								docList.get(j))+"\n");
				

			}

	}
}
