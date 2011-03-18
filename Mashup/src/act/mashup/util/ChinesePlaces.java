package act.mashup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import act.mashup.global.KV;

public class ChinesePlaces {
	//����ģʽ
	private static final ChinesePlaces instance = new ChinesePlaces ();
	private Set<String> placesSet;

	private ChinesePlaces() {
		try {
		placesSet=new HashSet<String>();
		String dictPathStr=getClass().getResource("/").toString()+KV.placesDictPath;
		URL dictPath=new URL(dictPathStr);
		File dict = new File(dictPath.toURI());
		Scanner scanner;
			scanner = new Scanner(dict,"utf-8");
			int i=0;
			while(scanner.hasNext()){
				placesSet.add(scanner.next());
				i++;
			}
			Log.logger.debug("Placees Dict loaded. Size="+i);
		} catch (FileNotFoundException e) {
			Log.logger.fatal(e);
		} catch (URISyntaxException e) {
			Log.logger.fatal(e);
		} catch (MalformedURLException e) {
			Log.logger.fatal(e);
		}
		
	}

	//��õ���
	public static synchronized ChinesePlaces getInstance() {
		return instance;
	}
	
	public boolean FindPlace(String s){
		return placesSet.contains(s);
			
	}

}
