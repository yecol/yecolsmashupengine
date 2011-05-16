package act.mashup.test;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.GoogleAPI;
import com.google.api.Language;


public final class Translate extends GoogleAPI {
	
	/**
	 * Constants.
	 */
    private static final String
    
    //https://www.googleapis.com/language/translate/v2?key=INSERT-YOUR-KEY&source=en&target=de&q=Hello%20world
    		//LANG_PARAM = "&langpair=",
    		//TEXT_PARAM = "&q=",
    		//PIPE_PARAM = "%7C",
    		URL = "https://www.googleapis.com/language/translate/v2?key=AIzaSyDvQkoudmKjqjSMWp81U4s30ki5yQU307A&source=#FROM#&target=#TO#&q=";
    		//PARAMETERS = "v2?key=AIzaSyDvQkoudmKjqjSMWp81U4s30ki5yQU307A&source=#FROM#&target=#TO#&q=";

    /**
     * Translates text from a given Language to another given Language using Google Translate.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String execute(final String text, final Language from, final Language to) throws Exception {
    	//validateReferrer();
    	
    	final String wrapedURL=URL.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString())+URLEncoder.encode(text, ENCODING);
    	final URL url = new URL(wrapedURL);
    	//final String parameters = PARAMETERS.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString())
    	//		+URLEncoder.encode(text, ENCODING);
    	
    	final JSONObject json = retrieveJSON(url);
    	
    	return getJSONResponse(json);
    }

    /**
     * Translates an array of text Strings from a given Language to another given Language using Google Translate.
     * 
     * @param text The array of Strings to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated array of String results.
     * @throws Exception on error.
     */
    public static String[] execute(final String[] text, final Language from, final Language to) throws Exception {
    	validateReferrer();
    	
    	final Language[] fromArgs = new Language[text.length];
    	final Language[] toArgs = new Language[text.length];
    	
    	for (int i = 0; i<text.length; i++) {
    		fromArgs[i] = from;
    		toArgs[i] = to;
    	}
    	
    	return execute(text, fromArgs, toArgs);
    }

    /**
     * Translates a String from a given Language to an Array of Languages using Google Translate.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The array of Languages to translate to.
     * @return The translated array of String results.
     * @throws Exception on error.
     */
    public static String[] execute(final String text, final Language from, final Language[] to) throws Exception {
    	validateReferrer();
    	
    	final String[] textArgs = new String[to.length];
    	final Language[] fromArgs = new Language[to.length];
    	
    	for (int i = 0; i<to.length; i++) {
    		textArgs[i] = text;
    		fromArgs[i] = from;
    	}
    	
    	return execute(textArgs, fromArgs, to);
    }

    /**
     * Translates text from a given Language to another given Language using Google Translate.
     * 
     * @param text The array of Strings to translate.
     * @param from The array of Language codes to translate from.
     * @param to The array of Language codes to translate to.
     * @return The translated array of String results.
     * @throws Exception on error.
     */
    
    public static String[] execute(final String[] text, final Language from[], final Language[] to) throws Exception {
    	validateReferrer();
    	
    	if (text.length != from.length || from.length != to.length) {
    		throw new Exception(
    				"[google-api-translate-java] The same number of texts, from and to languages must be supplied.");
    	}
    	
    	if (text.length == 1) {
    		return new String[] { execute(text[0], from[0], to[0]) };
    	}
    	
    	final String[] responses = new String[text.length];
    	
    	final StringBuilder parametersBuilder = new StringBuilder();
    	
    	//parametersBuilder.append(PARAMETERS.replaceAll("#FROM#", from[0].toString()).replaceAll("#TO#", to[0].toString()));
    	parametersBuilder.append(URLEncoder.encode(text[0], ENCODING));
    	
    	for (int i = 1; i<text.length; i++) {
    		//parametersBuilder.append(LANG_PARAM);
    		parametersBuilder.append(from[i].toString());
    		//parametersBuilder.append(PIPE_PARAM);
    		parametersBuilder.append(to[i].toString());
    		//parametersBuilder.append(TEXT_PARAM);
    		parametersBuilder.append(URLEncoder.encode(text[i].toString(), ENCODING));
    	}
    	
    	final URL url = new URL(URL);
    	
    	final JSONArray json = retrieveJSON(url, parametersBuilder.toString()).getJSONArray("responseData");
    	
    	for (int i = 0; i<json.length(); i++) {
    		final JSONObject obj = json.getJSONObject(i);
    	
	    	responses[i] = getJSONResponse(obj);
    	}
    	
    	return responses;
    }
    
    /**
     * Returns the JSON response data as a String. Throws an exception if the status is not a 200 OK.
     * 
     * @param json The JSON object to retrieve the response data from.
     * @return The responseData from the JSONObject.
     * @throws Exception If the responseStatus is not 200 OK.
     */
    private static String getJSONResponse(final JSONObject json) throws Exception {
    	//if (json.getString("responseStatus").equals("200")) {
    		final String translatedText = json.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText");
    		return translatedText;
    		//return HTMLEntities.unhtmlentities(translatedText);
    	//} else {
    		//throw new Exception("Google returned the following error: [" +json.getString("responseStatus") +"] "
    		//		+json.getString("responseDetails"));
    	//}
    }
    
    public static void main(String[] args) throws Exception{
    	System.out.println(Translate.execute("hello world",Language.ENGLISH,Language.CHINESE_SIMPLIFIED));
    }
    
    
}
