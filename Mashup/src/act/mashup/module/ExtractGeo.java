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
		System.out.println("new version");
		for(int index=0;index<items.size();index++){
			string=items.get(index).getValue("title");
			segments=cs.getSegments(string);
			String place=ExtractGeoInformation(segments);
			if(place==null){
				string=items.get(index).getValue("description");
				segments=cs.getSegments(string);
				place=ExtractGeoInformation(segments);
			}
			if(place!=null&&place.length()!=0){
				items.get(index).setValue("place", place);
				System.out.println(place);
			}
			else if(addUngeoItems.equals("0")){
				//it.remove();		
			}		
		}
		System.out.println("this is ok:size="+items.size());
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
