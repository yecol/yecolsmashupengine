package act.mashup.util.Similarity;

import java.util.ArrayList;
import java.util.Iterator;

import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.util.Log;

public class SimilarityDetector {
	private ArrayList<Item> items;
	private ArrayList<Integer> flags;
	private Integer _length;
	private int a, b;

	public SimilarityDetector(ArrayList<Item> items) {
		this.items = items;
		_length = items.size();
		flags = new ArrayList<Integer>();
		for (int i = 0; i < _length; i++)
			flags.add(0);
		a = 0;
		b = 0;
	}

	public void Detect() {
		if (_length == 1)
			return;
		else {
			for (; a < _length - 1; a++) {
				if (flags.get(a) != 1) {
					Item curItem = items.get(a);
					Text curText = new Text(curItem.getValue("title"));
					for (b = a + 1; b < _length; b++) {
						if (flags.get(b) != 1) {
							Item refItem = items.get(b);
							Text refText = new Text(refItem.getValue("title")+refItem.getValue("description"));
							//double similarity = curText.ComputeSimilarity2(refText);
							double similarity = curText.ComputeSimilarity(refText);
							//Log.logger.info(refItem.toString()+"\n"+curItem.toString());
							//Log.logger.info("s1="+similarity1+"s2="+similarity);
							if (similarity > KV.similarityThrashhold) {
								flags.set(b, 1);
								items.get(a).addRank();
							}
						}
					}
				}
			}
			Item _item;
			Iterator fIt = flags.iterator();
			Iterator iIt = items.iterator();
			for (; fIt.hasNext();) {
				Integer i = (Integer) fIt.next();
				_item = (Item) iIt.next();
				_item.rankToMap();
				if (i == 1) {
					fIt.remove();
					iIt.remove();
				}
			}
		}
	}
	public static void main(String[] ar){
		Text a=new Text("����");
		Text b=new Text("����");
		System.out.println(a.ComputeSimilarity(b));
		
	}

}
