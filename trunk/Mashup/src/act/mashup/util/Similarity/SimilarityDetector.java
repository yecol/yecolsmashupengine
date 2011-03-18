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
							double similarity = curText.ComputeSimilarity2(refText);
							/*
							 * if (similarity > 0.4) { Log.logger.debug("a=" + a
							 * + ",b=" + b); Log.logger.debug("curText=" +
							 * curText.getMyWord()); Log.logger.debug("refText="
							 * + refText.getMyWord()); Log.logger.debug("simi="
							 * + similarity); }
							 */
							if (similarity > KV.similarityThrashhold) {
								flags.set(b, 1);
								items.get(a).addRank();
								Log.logger.debug("Add a=" + a + ",rank=" + items.get(a).getRank());
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

}
