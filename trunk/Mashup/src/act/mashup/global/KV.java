package act.mashup.global;

import org.jdom.Namespace;

public final class KV {

	// Key-Values

	// Namespace
	public static final Namespace em = Namespace.getNamespace("em", "http://www.example.org/EngineModel");
	public static final Namespace gf = Namespace.getNamespace("gf", "http://www.example.org/GeneralFigure");

	// Url address for geo-tag news
	public static final String geoUrlPrefixWithUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=true&feedUrl=";
	public static final String geoUrlPrefixWithoutUngeo = "http://ws.geonames.org/rssToGeoRSS?geoRSS=simple&addUngeocodedItems=false&feedUrl=";

	// Sogou Dictionary
	public static final String sogouDictPath = "/SogoulabDic.dic";

	// Places Dictionary
	public static final String placesDictPath = "/PlacesDic.dic";
	
	public static final String log4JPropertiesFile="log4j.properties";
	
	// Similarity Thrashhold 
	public static final double similarityThrashhold=0.96;
	
	//Google API Key
	public static final String ENCODING = "UTF-8";
	public static final String googleAPIKey="AIzaSyDvQkoudmKjqjSMWp81U4s30ki5yQU307A";
	
	//Yahoo Key
	public static final String yahooAPIKey="dj0yJmk9a014emVnYk1xQUl5JmQ9WVdrOVZFVkNibkZCTkdjbWNHbzlNelk0T1RBek16WXkmcz1jb25zdW1lcnNlY3JldCZ4PTBl";
}
