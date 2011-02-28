package act.mashup.global;

import org.jdom.Namespace;

public final class KV {
	
	//Key-Values
	
	//Namespace
	public static final Namespace em = Namespace.getNamespace("em", "http://www.example.org/EngineModel");
	public static final Namespace gf = Namespace.getNamespace("gf", "http://www.example.org/GeneralFigure");

	//Url address for geo-tag news
	public static final String geoUrlPrefixWithUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=true&feedUrl=";
	public static final String geoUrlPrefixWithoutUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=false&feedUrl=";

}
