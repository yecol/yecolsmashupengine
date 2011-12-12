package act.mashup.module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import act.mashup.global.EngineNode;
import act.mashup.global.KV;
import act.mashup.global.Position;
import act.mashup.global.Result;
import act.mashup.util.LocalPlacesSearch;

public class LocalSearch extends AbstractListModule {

	/**
	 * @param args
	 */
	Position position;
	LocalPlacesSearch localPlacesSearch;
	ResultSet rs;
	
	
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		// TODO Auto-generated method stub
		localPlacesSearch=LocalPlacesSearch.getInstance();
		
		
		//position = en.getParas().getChildTextTrim("keyword", KV.gf);
		//System.out.println(keyword);
		
	}

	@Override
	protected void Execute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LocalPlacesSearch locale=LocalPlacesSearch.getInstance();
		ResultSet rs=locale.SearchByPosition(new Position(39.28, 116.0), LocalPlacesSearch.TYPE_HOTEL);
		try {
			System.out.println(rs.getFetchSize());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
