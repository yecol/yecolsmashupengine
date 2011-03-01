package act.mashup.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import act.mashup.global.KV;

public class ChinesePlaces {
	//����ģʽ
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

	//��õ���
	public static synchronized ChinesePlaces getInstance() {
		return instance;
	}
	
	public boolean FindPlace(String s){
		return placesSet.contains(s);
			
	}
	
	public static void main(String[] args){
		ChinesePlaces cp=ChinesePlaces.getInstance();
		System.out.println("over");
		System.out.println(cp.FindPlace("�������պ����ѧ"));
		System.out.println(cp.FindPlace("����԰"));
	}

}
