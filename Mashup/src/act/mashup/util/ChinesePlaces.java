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
	//单例模式
	private static final ChinesePlaces instance = new ChinesePlaces ();
	private Set<String> placesSet;

	private ChinesePlaces() {
		try {
		placesSet=new HashSet<String>();
		String dictPathStr=getClass().getResource("/").toString()+KV.placesDictPath;
		URL dictPath=new URL(dictPathStr);
		//System.out.println("RESOURCES:"+getClass().getResource("/").toString());
		File dict = new File(dictPath.toURI());
		//File dict = new File(KV.placesDictPath);
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

	//获得单例
	public static synchronized ChinesePlaces getInstance() {
		return instance;
	}
	
	public boolean FindPlace(String s){
		return placesSet.contains(s);
			
	}
	
	public static void main(String[] args){
		ChinesePlaces cp=ChinesePlaces.getInstance();
		System.out.println("over");
		System.out.println(cp.FindPlace("北京航空航天大学"));
		System.out.println(cp.FindPlace("莱茵花园"));
	}

}
