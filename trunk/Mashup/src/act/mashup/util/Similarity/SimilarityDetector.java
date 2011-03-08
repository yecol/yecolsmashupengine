package act.mashup.util.Similarity;

import java.util.ArrayList;
import java.util.Iterator;

import act.mashup.global.Item;
import act.mashup.global.KV;

public class SimilarityDetector {
	private ArrayList<Item> items;
	private Integer _length;
	private int a,b,c;
	
	public SimilarityDetector(ArrayList<Item> items) {
		this.items=items;
		_length=items.size();
		a=0;
		b=0;
		c=0;
	}
	
	public void Detect(){
		if(_length==1)
			return;
		else{
			for(Iterator<Item> curIt=newItems.iterator();curIt.hasNext();a++){
				Item curItem=curIt.next();
				Text curText=new Text(curItem.getValue("title")+curItem.getValue("description"));
				for(;a<_length;a++,b++){
					Item refItem= items.get(a);
					Text refText=new Text(refItem.getValue("title")+refItem.getValue("description"));
					System.out.println("c="+c+" a="+a+" b="+b+"title:"+curItem.getValue("title")+" v.s "+refItem.getValue("title")+" v="+curText.ComputeSimilarity(refText));
					if(curText.ComputeSimilarity(refText)>KV.similarityThrashhold){
						curIt
					}
				}
				
			}
		}
	}

}
