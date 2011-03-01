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

		// 计算词频
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

		// 计算词的特征值
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
		// 去掉无用词
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
				"  自驾游归来，经历那么在这边呢，不相同的吧。了长途跋涉的爱车损耗可不小，给爱车做个节后检修保养，以防爱车“小病成大病”。对此，美车饰专家给出了如下建议：车辆除尘清洁　　旅途中车身上总会沾染许多灰尘、柏油、虫尸等，如不及时清洗，这些污物会影响车辆美观，甚至腐蚀车漆。除了车身，轿厢内的清洁也不容忽视，尤其是顶棚和地毯，容易积存灰尘，让人看着不舒服，时间一长还会滋长霉斑、细菌。因此洗车同时应做个“内饰清洗”，彻底清洁爱车。　　底盘防护出游中难免有泥污溅到底盘上，如果你不是通过电脑洗车点而是在普通洗车摊洗车，对泥污的清除不彻底会锈蚀底盘。如果出游中底盘有过刮蹭，应及早去专业店检查，否则可能导致油底壳破裂而漏油。若想避免底盘受伤或锈蚀，最好给车“底盘装甲”处理。轮胎检测轮胎经过长途行驶，容易磨损和胎压不足，归来后应做个轮胎检测，查看胎压是否正常、有无明显伤痕，磨损过度或存在外伤的轮胎应立即更换，否则将导致爆胎。此外经过长时间颠簸轮胎定位会失效，出现车辆跑偏、跳动，四轮定位和动平衡可帮助调校轮胎定位。油液检查　　经过长途行驶，机油、冷却液消耗非常大，归来后应及时查看，余量不足应马上补充。如机油、冷却液出现发黑、变质就立即更换，不过更换前需彻底清洁油箱、水箱，防止旧液污染。此外，郊外灰尘比市内多，若空滤积尘过多应及时更换，以免进气受阻。　　美车饰·汽车百货现正举办“欢庆‘五一’·车行天下”活动，2006年5月13日前，凡光临美车饰·汽车百货的私车主均可免费享受轮胎安全检测、轮胎充氮气、空调风口检测清洗等服务。活动期间，凡在美车饰·汽车百货接受“光触媒”服务，获赠价值400元的“内饰清洗”；“底盘装甲”产品“买五送一”超值优惠；接受“PPS电泳镀膜”服务，获赠价值180元的“应急工具箱”。");
		Document doc2 = new Document(
				"　　春夏之交，是汽车污染最严重的时刻。许多车主在驾车时通常会闻到一股“异味”，而多数车主也仅仅觉得难闻而已，还可以忍受。殊不知，这种异味就是车内空气污染的标志。中国室内空气检测中心的调查表明：车内空气污染物主要是甲醛和苯超标。甲醛多是来自坐椅沙发垫、车顶装饰等装饰材料；而苯主要来自胶粘剂。　　由于汽车空间狭小，车内空气流量有限，加上汽车密封性好，因此车内的有害气体超标对人体造成的危害性就显得更大。　　车内空气污染不仅导致车内空气味道难闻，更严重的是，这些有害物质可能会对神经系统、免疫系统、内分泌系统以及生殖系统产生不利影响，甚至有可能致癌。一般来讲，车内装饰越多，产生车内空气污染的可能越大。特别是在夏季，由于开空调，车窗紧闭，导致车内空气不能及时流通，进一步加剧了车内空气的污染。长期在污染的环境中驾车，会导致驾驶员头晕、困倦、咳嗽等不良反应，进而导致情绪压抑烦躁、注意力无法集中，导致交通事故。　　健康提示：新购或刚进行过室内装修的车辆，在使用前，应先进行汽车室内消毒。如果感到自己的车内有异味，我们也建议您为了自己的身体健康，考虑进行一次车内消毒。在进行车内消毒时，应尽量选择专业的产品或设备。而空气清新剂、香水只能起到压制异味的作用，并不能消除车内的苯、甲醛等有害物质。");
		Document doc3 = new Document(
				"  我要怎么判断这是同一条新闻呢。难啊，有的时候讲的是同一间事。自驾游归去来，这经历了长途跋涉的爱车损耗可不小，给爱车做个节后检修保养，以防爱车“小病成大病”。对此，美车饰专家给出了如下建议：车辆除尘清洁　　旅途中车身上总会沾染许多灰尘、柏油、虫尸等，如不及时清洗，这些污物会影响车辆美观，甚至腐蚀车漆。除了车身，轿厢内的清洁也不容忽视，尤其是顶棚和地毯，容易积存灰尘，让人看着不舒服，时间一长还会滋长霉斑、细菌。因此洗车同时应做个“内饰清洗”，彻底清洁爱车。　　底盘防护出游中难免有泥污溅到底盘上，如果你不是通过电脑洗车点而是在普通洗车摊洗车，对泥污的清除不彻底会锈蚀底盘。如果出游中底盘有过刮蹭，应及早去专业店检查，否则可能导致油底壳破裂而漏油。若想避免底盘受伤或锈蚀，最好给车“底盘装甲”处理。轮胎检测轮胎经过长途行驶，容易磨损和胎压不足，归来后应做个轮胎检测，查看胎压是否正常、有无明显伤痕，磨损过度或存在外伤的轮胎应立即更换，否则将导致爆胎。此外经过长时间颠簸轮胎定位会失效，出现车辆跑偏、跳动，四轮定位和动平衡可帮助调校轮胎定位。油液检查　　经过长途行驶，机油、冷却液消耗非常大，归来后应及时查看，余量不足应马上补充。如机油、冷却液出现发黑、变质就立即更换，不过更换前需彻底清洁油箱、水箱，防止旧液污染。此外，郊外灰尘比市内多，若空滤积尘过多应及时更换，以免进气受阻。　　美车饰·汽车百货现正举办“欢庆‘五一’·车行天下”活动，2006年5月13日前，凡光临美车饰·汽车百货的私车主均可免费享受轮胎安全检测、轮胎充氮气、空调风口检测清洗等服务。活动期间，凡在美车饰·汽车百货接受“光触媒”服务，获赠价值400元的“内饰清洗”；“底盘装甲”产品“买五送一”超值优惠；接受“PPS电泳镀膜”服务，获赠价值180元的“应急工具箱”。");

		Document doc4 = new Document(
				"中新网2月25日电 据华尔街日报中文网报道，苹果公司昨日发布了新款MacBook Pro笔记本电脑，新款MacBook Pro电脑将采用英特尔新一代处理器及其高速数据传输技术。苹果公司表示，更新版MacBook Pro笔记本电脑将是市场上首款采用高速数据传输技术的产品，该技术名为Thunderbolt。该公司表示，预计Thunderbolt技术将作为新的高速数据传输标准而得到广泛应用。苹果公司还表示，更新版MacBook Pro电脑使用英特尔最新的双核和四核Intel Core处理器，其运行速度将达到原来的两倍。");
		Document doc5 = new Document(
				"北京时间2月25日早间消息，据国外媒体报道，苹果新款MacBook Pro笔记本的电池寿命可能为7小时，不及此前15英寸和17英寸版的8到9小时和13英寸版的10小时。报道称，苹果已经开始使用一种新的电池寿命测试方法，通过这种名为“无线网络协议测试”的方法进行测试的电池寿命更加符合现实使用情况。这种测试方法的具体操作是，将每部被测试手机的显示亮度定为50%，然后上网浏览25个最流行的网站，反复使用这些网站的主要功能，其中包括播放Flash视频等。苹果指出，基于手机配置和使用模式的差异，测试结果可能也会有所不同。");

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
