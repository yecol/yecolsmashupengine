package act.mashup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import act.mashup.global.KV;

public class ChinesePlaces {
	//单例模式
	private static final ChinesePlaces instance = new ChinesePlaces ();
	private Set<String> placesSet;

	private ChinesePlaces() {
		placesSet=new HashSet<String>();
		File dict = new File(KV.placesDictPath);
		Scanner scanner;
		try {
			scanner = new Scanner(dict,"utf-8");
			int i=0;
			while(scanner.hasNext()){
				placesSet.add(scanner.next());
				i++;
			}
			System.out.println("size"+i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
