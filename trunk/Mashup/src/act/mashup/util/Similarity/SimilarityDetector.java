package act.mashup.util.Similarity;

import java.util.ArrayList;
import java.util.Iterator;

import act.mashup.global.Item;
import act.mashup.global.KV;

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
				Item curItem = items.get(a);
				Text curText = new Text(curItem.getValue("title") + curItem.getValue("description"));
				for (b = a + 1; b < _length; b++) {
					if (flags.get(b) != 1) {
						Item refItem = items.get(b);
						Text refText = new Text(refItem.getValue("title") + refItem.getValue("description"));
						if (curText.ComputeSimilarity(refText) > KV.similarityThrashhold) {
							flags.set(b, 1);
							items.get(a).addRank();
						}
						// System.out.println(" a=" + a + " b=" + b + "title:" +
						// curItem.getValue("title") + " v.s " +
						// refItem.getValue("title") + " v="
						// + curText.ComputeSimilarity(refText));
					}
				}
			}
			Iterator fIt = flags.iterator();
			Iterator iIt = items.iterator();
			for (; fIt.hasNext();) {
				Integer i = (Integer) fIt.next();
				iIt.next();
				if (i == 1) {
					fIt.remove();
					iIt.remove();
				}
			}
		}
	}

}
