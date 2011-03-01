package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.ChinesePlaces;
import act.mashup.util.ChineseSegment;

public class ExtractGeo extends AbstractModule {
	
	private Integer in;
	private ChineseSegment cs;
	private ChinesePlaces cp;
	private String addUngeoItems;
	
	private String string;
	private ArrayList<String> segments;
	
	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		addUngeoItems = en.getParas().getChildTextTrim("addUngeoItems", KV.gf);
		cs=ChineseSegment.getInstance();
		cp=ChinesePlaces.getInstance();
		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
	}

	@Override
	protected void Execute() throws Exception {
		// TODO Auto-generated method stub
		cs.testSegment();
		for(Iterator it=items.iterator();it.hasNext();){
			Item item=(Item)it.next();
			string=item.getValue("title");
			segments=cs.getSegments(string);
			//System.out.println(segments.toString());
			String place=ExtractGeoInformation(segments);
			if(place==null){
				string=item.getValue("description");
				segments=cs.getSegments(string);
				//System.out.println(segments.toString());
				place=ExtractGeoInformation(segments);
			}
			if(place!=null&&place.length()!=0){
				item.setValue("place", place);
			}
			else if(addUngeoItems.equals("0")){
				it.remove();	
			}		
		}
		//System.out.println("this is ok:size="+items.size());
		rlt.SetResultList(items);

	}
	
	private String ExtractGeoInformation(ArrayList<String> segments){
		for(String segment:segments){
			if(cp.FindPlace(segment)==true)
				return segment;
		}
		return null;		
	}

}
